package com.thomasvitale.ai.spring.guardrails;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.decision.DecisionModel;
import org.springframework.ai.decision.DecisionPrompt;
import org.springframework.ai.decision.DecisionResponse;
import org.springframework.ai.decision.question.YesNoQuestion;

import java.util.ArrayList;
import java.util.List;

public class DecisionOutputGuardrailAdvisor implements CallAdvisor {

    private static final double ANSWERED_THRESHOLD = 0.35;

    public static final String HANDOFF_RESPONSE = "I'm sorry, I couldn't find an answer to your question. I'm passing it on to one of our support agents, who will get back to you.";

    private final DecisionModel decisionModel;

    public DecisionOutputGuardrailAdvisor(DecisionModel decisionModel) {
        this.decisionModel = decisionModel;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain chain) {
        ChatClientResponse chatClientResponse = chain.nextCall(chatClientRequest);

        ChatResponse chatResponse = chatClientResponse.chatResponse();
        if (chatResponse == null || chatResponse.getResult() == null) {
            return chatClientResponse;
        }

        List<Message> conversation = new ArrayList<>(chatClientRequest.prompt().getInstructions().stream()
                .filter(message -> message.getMessageType() != MessageType.SYSTEM)
                .toList());
        conversation.add(chatResponse.getResult().getOutput());

        DecisionResponse decision = decisionModel.call(DecisionPrompt.builder()
                .conversation(conversation)
                .question("answered", YesNoQuestion.builder()
                        .text("Does the last assistant message answer the user's question?")
                        .whenYes("It gives the information the user asked for")
                        .whenNo("It dodges the question, refuses to answer, says the information isn't available, or talks about something else")
                        .build())
                .build());

        if (decision.isRefused("answered") || !decision.getYesNo("answered").isYes(ANSWERED_THRESHOLD)) {
            return chatClientResponse.mutate()
                    .chatResponse(ChatResponse.builder()
                            .generations(List.of(new Generation(AssistantMessage.builder().content(HANDOFF_RESPONSE).build())))
                            .build())
                    .build();
        }
        return chatClientResponse;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 1;
    }

}
