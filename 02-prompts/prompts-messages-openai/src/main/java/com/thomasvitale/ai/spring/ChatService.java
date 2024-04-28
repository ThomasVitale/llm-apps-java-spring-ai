package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
class ChatService {

    private final ChatClient chatClient;

    private final Resource systemMessageResource;

    ChatService(ChatClient chatClient, @Value("classpath:/prompts/system-message.st") Resource systemMessageResource) {
        this.chatClient = chatClient;
        this.systemMessageResource = systemMessageResource;
    }

    String chatWithSingleMessage(String message) {
        var userMessage = new UserMessage(message);
        var prompt = new Prompt(userMessage);
        return chatClient.call(userMessage);
    }

    String chatWithMultipleMessages(String message) {
        var systemMessage = new SystemMessage("""
                You are a helpful and polite assistant.
                Answer in one sentence using a very formal language
                and starting the answer with a formal greeting.
                """);
        var userMessage = new UserMessage(message);
        return chatClient.call(systemMessage, userMessage);
    }

    String chatWithExternalMessage(String message) {
        var systemMessage = new SystemMessage(systemMessageResource);
        var userMessage = new UserMessage(message);
        return chatClient.call(systemMessage, userMessage);
    }

}
