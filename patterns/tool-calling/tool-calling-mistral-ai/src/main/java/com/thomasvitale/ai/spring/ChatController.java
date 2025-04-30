package com.thomasvitale.ai.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatClient chatClient;
    private final Tools tools;

    ChatController(ChatClient.Builder chatClientBuilder, Tools tools) {
        this.chatClient = chatClientBuilder.build();
        this.tools = tools;
    }

    // METHODS

    @GetMapping("/chat/method/no-args")
    String chatMethodNoArgs() {
        return chatClient.prompt()
                .user("Welcome the user to the library")
                .tools(tools)
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
                .tools(tools)
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
                .tools(tools)
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
                .tools(tools)
                .call()
                .content();
    }

    // FUNCTIONS

    @GetMapping("/chat/function/void-input")
    String chatFunctionVoidInput() {
        return chatClient.prompt()
                .user("Welcome the users to the library")
                .toolNames(Functions.WELCOME)
                .call()
                .content();
    }

    @GetMapping("/chat/function/void-input/callback")
    String chatFunctionVoidInputCallback() {
        return chatClient.prompt()
                .user("Welcome the users to the library")
                .toolCallbacks(FunctionToolCallback.builder("sayWelcome", (input) -> {
                            logger.info("CALLBACK - Welcoming users to the library");
                        })
                        .description("Welcome users to the library")
                        .inputType(Void.class)
                        .build())
                .call()
                .content();
    }

    @GetMapping("/chat/function/void-output")
    String chatFunctionVoidOutput(String user) {
        var userPromptTemplate = "Welcome {user} to the library";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("user", user)
                )
                .toolNames(Functions.WELCOME_USER)
                .call()
                .content();
    }

    @GetMapping("/chat/function/void-output/callback")
    String chatFunctionVoidOutputCallback(String user) {
        var userPromptTemplate = "Welcome {user} to the library";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("user", user)
                )
                .toolCallbacks(FunctionToolCallback.builder("welcomeUser", (input) -> {
                            logger.info("CALLBACK - Welcoming {} to the library", ((Functions.User) input).name());
                        })
                        .description("Welcome a specific user to the library")
                        .inputType(Functions.User.class)
                        .build())
                .call()
                .content();
    }

    @GetMapping("/chat/function/single")
    String chatFunctionSingle(String authorName) {
        var userPromptTemplate = "What books written by {author} are available in the library?";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("author", authorName)
                )
                .toolNames(Functions.BOOKS_BY_AUTHOR)
                .call()
                .content();
    }

    @GetMapping("/chat/function/single/callback")
    String chatFunctionSingleCallback(String authorName) {
        Function<BookService.Author, List<BookService.Book>> function = author -> {
            logger.info("CALLBACK - Getting books by author: {}", author.name());
            return new BookService().getBooksByAuthor(author);
        };
        var userPromptTemplate = "What books written by {author} are available in the library?";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("author", authorName)
                )
                .toolCallbacks(FunctionToolCallback.builder("availableBooksByAuthor", function)
                        .description("Get the list of books written by the given author available in the library")
                        .inputType(BookService.Author.class)
                        .build())
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
                .toolNames(Functions.AUTHORS_BY_BOOKS)
                .call()
                .content();
    }

    @GetMapping("/chat/function/list/callback")
    String chatFunctionListCallback(String bookTitle1, String bookTitle2) {
        Function<BookService.Books, List<BookService.Author>> function = books -> {
            logger.info("CALLBACK - Getting authors by books: {}", books.books().stream().map(BookService.Book::title).toList());
            return new BookService().getAuthorsByBook(books.books());
        };
        var userPromptTemplate = "What authors wrote the books {bookTitle1} and {bookTitle2} available in the library?";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("bookTitle1", bookTitle1)
                        .param("bookTitle2", bookTitle2)
                )
                .toolCallbacks(FunctionToolCallback.builder("authorsByAvailableBooks", function)
                        .description("Get the list of authors who wrote the given books available in the library")
                        .inputType(BookService.Books.class)
                        .build())
                .call()
                .content();
    }

}
