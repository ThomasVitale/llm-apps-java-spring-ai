package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private final BookService bookService;
    private final ChatClient chatClient;
    private final Tools tools;

    ChatController(BookService bookService, ChatClient.Builder chatClientBuilder, Tools tools) {
        this.bookService = bookService;
        this.chatClient = chatClientBuilder.build();
        this.tools = tools;
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

    @GetMapping("/chat/function/list")
    String chatFunctionList(String bookTitle1, String bookTitle2) {
        var userPromptTemplate = "What authors wrote the books {bookTitle1} and {bookTitle2} available in the library?";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("bookTitle1", bookTitle1)
                        .param("bookTitle2", bookTitle2)
                )
                .functions("authorsByBooks")
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
                .functions(
                        FunctionCallback.builder()
                                .function("BooksByAuthor", bookService::getBooksByAuthor)
                                .description("Get the list of books written by the given author available in the library")
                                .inputType(BookService.Author.class)
                                .responseConverter(Object::toString)
                                .build()
                )
                .call()
                .content();
    }

    @GetMapping("/chat/method/no-args")
    String chatMethodNoArgs() {
        return chatClient.prompt()
                .user("Welcome the user to the library")
                .functions(FunctionCallback.builder()
                        .method("welcome")
                        .description("Welcome users to the library")
                        .targetObject(tools)
                        .build()
                )
                .call()
                .content();
    }

    @GetMapping("/chat/method/void")
    String chatMethodVoid(String user) {
        var userPromptTemplate = "Welcome {user} to the library";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("user", user)
                )
                .functions(FunctionCallback.builder()
                        .method("welcomeUser", String.class)
                        .description("Welcome a specific user to the library")
                        .targetObject(tools)
                        .build()
                )
                .call()
                .content();
    }

    @GetMapping("/chat/method/single")
    String chatMethodSingle(String authorName) {
        var userPromptTemplate = "What books written by {author} are available in the library?";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("author", authorName)
                )
                .functions(FunctionCallback.builder()
                        .method("booksByAuthor", String.class)
                        .description("Get the list of books written by the given author available in the library")
                        .targetObject(tools)
                        .build()
                )
                .call()
                .content();
    }

    @GetMapping("/chat/method/list")
    String chatMethodList(String bookTitle1, String bookTitle2) {
        var userPromptTemplate = "What authors wrote the books {bookTitle1} and {bookTitle2} available in the library?";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("bookTitle1", bookTitle1)
                        .param("bookTitle2", bookTitle2)
                )
                .functions(FunctionCallback.builder()
                        .method("authorsByBooks", List.class)
                        .description("Get the list of authors who wrote the given books available in the library")
                        .targetObject(tools)
                        .build()
                )
                .call()
                .content();
    }

    @GetMapping("/chat/method/non-public")
    String chatMethodNonPublic(String authorName) {
        var userPromptTemplate = "What books written by {author} are available in the library?";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("author", authorName)
                )
                .functions(FunctionCallback.builder()
                        .method("findBooksByAuthor", String.class)
                        .description("Get the list of books written by the given author available in the library")
                        .targetObject(tools)
                        .build()
                )
                .call()
                .content();
    }

}
