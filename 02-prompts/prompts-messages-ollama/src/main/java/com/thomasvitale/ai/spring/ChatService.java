package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Chat examples using the high-level ChatClient API.
 */
@Service
class ChatService {

    private final ChatClient chatClient;

    private final Resource systemMessageResource;

    ChatService(ChatClient.Builder chatClientBuilder, @Value("classpath:/prompts/system-message.st") Resource systemMessageResource) {
        this.chatClient = chatClientBuilder.build();
        this.systemMessageResource = systemMessageResource;
    }

    String chatWithSingleMessage(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    String chatWithMultipleMessages(String question) {
        var systemMessage = """
                You are a helpful and polite assistant.
                Answer in one sentence using a very formal language
                and starting the answer with a formal greeting.
                """;
        return chatClient.prompt()
                .system(systemMessage)
                .user(question)
                .call()
                .content();
    }

    String chatWithExternalMessage(String question) {
        var systemMessage = new SystemMessage(systemMessageResource);
        var userMessage = new UserMessage(question);
        return chatClient.prompt()
                .messages(systemMessage, userMessage)
                .call()
                .content();
    }

}
