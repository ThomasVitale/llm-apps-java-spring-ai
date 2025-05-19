package com.thomasvitale.ai.spring.guardrails;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.util.json.JsonParser;
import org.springframework.ai.util.json.schema.JsonSchemaGenerator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;

/**
 * An output guardrail that checks the output of a chat client call to ensure it is valid JSON.
 * If not, it reprompts the LLM to produce a valid JSON object.
 */
public class JsonOutputGuardrailAdvisor implements CallAdvisor {

    private final ChatClient chatClient;
    private final Type type;

    public JsonOutputGuardrailAdvisor(ChatClient.Builder chatClientBuilder, Type type) {
        Assert.notNull(chatClientBuilder, "chatClientBuilder cannot be null");
        Assert.notNull(type, "type cannot be null");
        this.chatClient = chatClientBuilder.build();
        this.type = type;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain chain) {
        ChatClientResponse chatClientResponse = chain.nextCall(chatClientRequest);

        String result = extractResponseText(chatClientResponse);
        if (!StringUtils.hasText(result)) {
            return chatClientResponse;
        }

        try {
            JsonParser.getObjectMapper().readValue(result, JsonParser.getObjectMapper().constructType(type));
        } catch (JsonProcessingException exception) {
            ChatResponse resultAfterReprompt = chatClient.prompt()
                    .system("""
                        Produce a valid JSON object from the input, which is wrongly formatted.
                        The JSON Schema you must adhere to is the following:
                        %s
                        
                        Currently, the following error is thrown by Jackson when trying to parse the wrongly formatted input.
                        %s
                        """.formatted(JsonSchemaGenerator.generateForType(type), exception.getMessage()))
                    .user(result)
                    .call()
                    .chatResponse();
            return chatClientResponse.mutate().chatResponse(resultAfterReprompt).build();
        }

        return chatClientResponse;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Nullable
    private String extractResponseText(ChatClientResponse chatClientResponse) {
        ChatResponse chatResponse = chatClientResponse.chatResponse();
        if (chatResponse == null) {
            return null;
        }

        Generation generation = chatResponse.getResult();
        if (generation == null) {
            return null;
        }

        AssistantMessage assistantMessage = generation.getOutput();
        if (assistantMessage == null) {
            return null;
        }

        return assistantMessage.getText();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ChatClient.Builder chatClientBuilder;
        private Type type;

        private Builder() {}

        public Builder chatClientBuilder(ChatClient.Builder chatClientBuilder) {
            this.chatClientBuilder = chatClientBuilder;
            return this;
        }

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        public JsonOutputGuardrailAdvisor build() {
            return new JsonOutputGuardrailAdvisor(chatClientBuilder, type);
        }
    }

}
