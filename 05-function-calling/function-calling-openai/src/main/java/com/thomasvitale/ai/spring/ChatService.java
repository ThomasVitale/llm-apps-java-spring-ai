package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * Chat examples using the high-level ChatClient API.
 */
@Service
class ChatService {

    private final BookService bookService;
    private final ChatClient chatClient;

    ChatService(BookService bookService, ChatClient.Builder chatClientBuilder) {
        this.bookService = bookService;
        this.chatClient = chatClientBuilder.build();
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

    String getAvailableBooksByWithExplicitFunction(String authorName) {
        var userPromptTemplate = "What books written by {author} are available to read?";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("author", authorName)
                )
                .function(
                        "BooksByAuthor",
                        "Get the list of available books written by the given author",
                        BookService.Author.class,
                        bookService::getBooksByAuthor
                )
                .call()
                .content();
    }

}
