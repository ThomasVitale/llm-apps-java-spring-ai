package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private final BookService bookService;
    private final ChatClient chatClient;

    ChatController(BookService bookService, ChatClient.Builder chatClientBuilder) {
        this.bookService = bookService;
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat/function")
    String chat(String authorName) {
        var userPromptTemplate = "What books written by {author} are available in the library?";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("author", authorName)
                )
                .functions("booksByAuthor")
                .call()
                .content();
    }

    @GetMapping("/chat/function/explicit")
    String chatVariant(String authorName) {
        var userPromptTemplate = "What books written by {author} are available in the library?";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("author", authorName)
                )
                .function(
                        "BooksByAuthor",
                        "Get the list of books written by the given author available in the library",
                        BookService.Author.class,
                        bookService::getBooksByAuthor
                )
                .call()
                .content();
    }

}
