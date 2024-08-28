package com.thomasvitale.ai.spring.model;

import com.thomasvitale.ai.spring.BookService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Chat examples using the low-level ChatModel API.
 */
@Service
class ChatModelService {

    private final BookService bookService;
    private final ChatModel chatModel;

    ChatModelService(BookService bookService, ChatModel chatModel) {
        this.bookService = bookService;
        this.chatModel = chatModel;
    }

    String getAvailableBooksBy(String authorName) {
        var userPromptTemplate = new PromptTemplate("""
                What books written by {author} are available to read?
                """);
        Map<String,Object> model = Map.of("author", authorName);
        var prompt = userPromptTemplate.create(model, OllamaOptions.builder()
                .withFunctions(Set.of("booksByAuthor"))
                .build());

        var chatResponse = chatModel.call(prompt);
        return chatResponse.getResult().getOutput().getContent();
    }

    String getAvailableBooksByWithExplicitFunction(String authorName) {
        var userPromptTemplate = new PromptTemplate("""
                What books written by {author} are available to read?
                """);
        Map<String,Object> model = Map.of("author", authorName);
        var prompt = userPromptTemplate.create(model, OllamaOptions.builder()
                .withFunctionCallbacks(List.of(
                        FunctionCallbackWrapper.builder(bookService::getBooksByAuthor)
                                .withDescription("Get the list of available books written by the given author")
                                .withName("BooksByAuthor")
                                .withInputType(BookService.Author.class)
                                .withResponseConverter(Object::toString)
                                .build()
                ))
                .build());

        var chatResponse = chatModel.call(prompt);
        return chatResponse.getResult().getOutput().getContent();
    }

}
