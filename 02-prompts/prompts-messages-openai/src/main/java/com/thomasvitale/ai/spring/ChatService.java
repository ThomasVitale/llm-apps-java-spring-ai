package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ChatService {

    private final ChatClient chatClient;

    private final Resource systemMessageResource;

    ChatService(ChatClient chatClient, @Value("classpath:/prompts/system-message.st") Resource systemMessageResource) {
        this.chatClient = chatClient;
        this.systemMessageResource = systemMessageResource;
    }

    AssistantMessage chatWithSingleMessage(String message) {
        var userMessage = new UserMessage(message);
        var prompt = new Prompt(userMessage);
        var chatResponse = chatClient.call(prompt);
        return chatResponse.getResult().getOutput();
    }

    AssistantMessage chatWithMultipleMessages(String message) {
        var systemMessage = new SystemMessage("""
                You are a helpful and polite assistant.
                Answer in one sentence using a very formal language
                and starting the answer with a formal greeting.
                """);
        var userMessage = new UserMessage(message);
        var prompt = new Prompt(List.of(systemMessage, userMessage));
        var chatResponse = chatClient.call(prompt);
        return chatResponse.getResult().getOutput();
    }

    AssistantMessage chatWithExternalMessage(String message) {
        var systemMessage = new SystemMessage(systemMessageResource);
        var userMessage = new UserMessage(message);
        var prompt = new Prompt(List.of(systemMessage, userMessage));
        var chatResponse = chatClient.call(prompt);
        return chatResponse.getResult().getOutput();
    }

}
