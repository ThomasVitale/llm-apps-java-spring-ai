package com.thomasvitale.ai.spring.model;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

/**
 * Chat examples using the low-level ChatModel API.
 */
@Service
class ChatModelService {

    private final ChatModel chatModel;

    ChatModelService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    String chatWithText(String question) {
        return chatModel.call(question);
    }

    ChatResponse chatWithPrompt(String question) {
        return chatModel.call(new Prompt(question));
    }

}
