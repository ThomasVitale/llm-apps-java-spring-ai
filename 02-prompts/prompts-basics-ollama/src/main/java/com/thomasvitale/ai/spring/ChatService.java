package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

/**
 * Chat examples using the high-level ChatClient API.
 */
@Service
class ChatService {

    private final ChatClient chatClient;

    ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    String chatWithText(String question) {
        return chatClient.prompt().user(question).call().content();
    }

    ChatResponse chatWithPrompt(String question) {
        return chatClient.prompt(new Prompt(question)).call().chatResponse();
    }

}
