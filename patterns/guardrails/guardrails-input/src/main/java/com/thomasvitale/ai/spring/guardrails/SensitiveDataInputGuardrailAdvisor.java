package com.thomasvitale.ai.spring.guardrails;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

import java.util.List;

/**
 * A guardrail advisor that checks for sensitive data in user input.
 * If sensitive data is detected, it disallows the LLM request and returns a default response.
 */
public class SensitiveDataInputGuardrailAdvisor implements CallAdvisor {

    private final ChatClient chatClient;

    public SensitiveDataInputGuardrailAdvisor(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain chain) {
        UserMessage userMessage = chatClientRequest.prompt().getUserMessage();

        InputGuardrailResult inputGuardrailResult = chatClient.prompt()
                .system("""
                        You are an EU safety guard, responsible for GDPR compliance.
                        Your task it to ensure the user question doesn't include any sensitive data,
                        such as credit card numbers, home addresses, social security numbers, or any
                        other information that qualifies as personally identifiable information (PII).
                        If you detect any of such sensitive data in the user question,
                        you must disallow it. If not, you can allow it.
                        """)
                .user(userMessage.getText())
                .call()
                .entity(InputGuardrailResult.class);

        if (inputGuardrailResult != null && Allowed.TRUE.equals(inputGuardrailResult.allowed())) {
            return chain.nextCall(chatClientRequest);
        } else {
            String response = "Computer says no!";
            return ChatClientResponse.builder()
                    .chatResponse(ChatResponse.builder()
                            .generations(List.of(new Generation(AssistantMessage.builder().content(response).build())))
                            .build())
                    .context(chatClientRequest.context())
                    .build();
        }
    }

    public enum Allowed {
        TRUE, FALSE;
    }

    public record InputGuardrailResult(Allowed allowed) {}

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ChatClient.Builder chatClientBuilder;

        private Builder() {}

        public Builder chatClientBuilder(ChatClient.Builder chatClientBuilder) {
            this.chatClientBuilder = chatClientBuilder;
            return this;
        }

        public SensitiveDataInputGuardrailAdvisor build() {
            return new SensitiveDataInputGuardrailAdvisor(chatClientBuilder);
        }
    }

}
