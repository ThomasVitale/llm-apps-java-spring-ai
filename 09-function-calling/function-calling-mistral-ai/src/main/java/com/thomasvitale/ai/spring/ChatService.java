package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.stereotype.Service;

@Service
class ChatService {

    private final ChatClient chatClient;

    ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    String getAvailableBooksBy(String authorName) {
        var userMessage = new UserMessage("What books written by %s are available to read?".formatted(authorName));
        var chatResponse = chatClient.call(new Prompt(
                userMessage,
                MistralAiChatOptions.builder()
                        .withFunction("booksByAuthor")
                        .build())
        );
        return chatResponse.getResult().getOutput().getContent();
    }

}
