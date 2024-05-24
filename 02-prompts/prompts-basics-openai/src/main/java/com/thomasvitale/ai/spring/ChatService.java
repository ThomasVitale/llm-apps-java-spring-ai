package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
class ChatService {

    private final ChatClient chatClient;

    ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    String chatWithText(String message) {
        return chatClient.prompt().user(message).call().content();
    }

    ChatResponse chatWithPrompt(String message) {
        return chatClient.prompt(new Prompt(message)).call().chatResponse();
    }

}
