package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.ChatClient;
import org.springframework.stereotype.Service;

@Service
class ChatService {

    private final ChatClient chatClient;

    ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    String getAvailableBooksBy(String authorName) {
        var userPromptTemplate = "What books written by {author} are available to read?";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("author", authorName)
                )
                .functions("booksByAuthor")
                .call()
                .content();
    }

}
