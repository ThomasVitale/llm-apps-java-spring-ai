package com.thomasvitale.ai.spring.model;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Chat examples using the low-level ChatModel API.
 */
@Service
class ChatModelService {

    private final ChatModel chatModel;

    private final Resource systemMessageResource;

    ChatModelService(ChatModel chatModel, @Value("classpath:/prompts/system-message.st") Resource systemMessageResource) {
        this.chatModel = chatModel;
        this.systemMessageResource = systemMessageResource;
    }

    String chatWithSingleMessage(String question) {
        var userMessage = new UserMessage(question);
        var prompt = new Prompt(userMessage);
        var chatResponse = chatModel.call(prompt);
        var assistantMessage = chatResponse.getResult().getOutput();
        return assistantMessage.getContent();
    }

    String chatWithMultipleMessages(String question) {
        var systemMessage = new SystemMessage("""
                You are a helpful and polite assistant.
                Answer in one sentence using a very formal language
                and starting the answer with a formal greeting.
                """);
        var userMessage = new UserMessage(question);
        var prompt = new Prompt(List.of(systemMessage, userMessage));
        var chatResponse = chatModel.call(prompt);
        var assistantMessage = chatResponse.getResult().getOutput();
        return assistantMessage.getContent();
    }

    String chatWithExternalMessage(String question) {
        var systemMessage = new SystemMessage(systemMessageResource);
        var userMessage = new UserMessage(question);
        var prompt = new Prompt(List.of(systemMessage, userMessage));
        var chatResponse = chatModel.call(prompt);
        var assistantMessage = chatResponse.getResult().getOutput();
        return assistantMessage.getContent();
    }

}
