package com.thomasvitale.ai.spring.model;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * Chat examples using the low-level ChatModel API.
 */
@Service
class ChatModelService {

    private final ChatModel chatModel;

    ChatModelService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    String getAvailableBooksBy(String authorName) {
        var userPromptTemplate = new PromptTemplate("""
                What books written by {author} are available to read?
                """);
        Map<String,Object> model = Map.of("author", authorName);
        var prompt = userPromptTemplate.create(model, MistralAiChatOptions.builder()
                .withFunctions(Set.of("booksByAuthor"))
                .build());

        var chatResponse = chatModel.call(prompt);
        return chatResponse.getResult().getOutput().getContent();
    }

}
