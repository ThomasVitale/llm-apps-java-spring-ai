package com.thomasvitale.ai.spring.guardrails;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.decision.DecisionModel;
import org.springframework.ai.decision.DecisionPrompt;
import org.springframework.ai.decision.DecisionResponse;
import org.springframework.ai.decision.question.YesNoQuestion;
import org.springframework.util.StringUtils;

import java.util.List;

public final class DecisionInputGuardrailAdvisor implements CallAdvisor {

    static final String SENSITIVE_DATA_RESPONSE = "For your safety, please don't share payment details or personal ID numbers in this chat. Remove them and ask your question again.";

    static final String OFF_TOPIC_RESPONSE = "I can only help with questions about our store, your orders, and our instruments.";

    private final DecisionModel decisionModel;

    public DecisionInputGuardrailAdvisor(DecisionModel decisionModel) {
        this.decisionModel = decisionModel;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain chain) {
        String userMessage = chatClientRequest.prompt().getUserMessage().getText();

        if (!StringUtils.hasText(userMessage)) {
            return block(chatClientRequest, OFF_TOPIC_RESPONSE);
        }

        DecisionResponse decision = decisionModel.call(DecisionPrompt.builder()
                .text(userMessage)
                .question("sensitiveData", YesNoQuestion.builder()
                        .text("Does this message contain sensitive data that must not be shared in a chat?")
                        .whenYes("It contains a credit card number, a CVV, a social security number, or a bank account number")
                        .whenNo("It contains none of those. Order numbers and instrument names are not sensitive")
                        .build())
                .question("onTopic", YesNoQuestion.builder()
                        .text("Is this message something the customer support of an online musical instrument store can help with?")
                        .whenYes("It asks about buying or owning an instrument or an accessory, or about orders, shipping, returns, billing, or the customer's account")
                        .whenNo("It is about anything else, such as cooking, travel, homework, music history, or general knowledge")
                        .build())
                .build());

        if (decision.isRefused("sensitiveData") || decision.getYesNo("sensitiveData").isYes()) {
            return block(chatClientRequest, SENSITIVE_DATA_RESPONSE);
        }
        if (decision.isRefused("onTopic") || !decision.getYesNo("onTopic").isYes()) {
            return block(chatClientRequest, OFF_TOPIC_RESPONSE);
        }
        return chain.nextCall(chatClientRequest);
    }

    private static ChatClientResponse block(ChatClientRequest chatClientRequest, String response) {
        return ChatClientResponse.builder()
                .chatResponse(ChatResponse.builder()
                        .generations(List.of(new Generation(AssistantMessage.builder().content(response).build())))
                        .build())
                .context(chatClientRequest.context())
                .build();
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
