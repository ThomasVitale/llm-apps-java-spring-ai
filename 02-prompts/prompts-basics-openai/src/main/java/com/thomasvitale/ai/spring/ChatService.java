package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
class ChatService {

    private final ChatClient chatClient;

    ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    String chatWithText(String message) {
        return chatClient.generate(message);
    }

    ChatResponse chatWithPrompt(String message) {
        return chatClient.generate(new Prompt(message));
    }

}
