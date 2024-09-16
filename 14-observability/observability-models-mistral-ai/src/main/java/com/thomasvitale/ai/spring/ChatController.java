package com.thomasvitale.ai.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
class ChatController {

    private final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatModel chatModel;

    ChatController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/chat")
    String chat(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        logger.info(message);
        return chatModel.call(message);
    }

    @GetMapping("/chat/generic-options")
    String chatWithGenericOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return chatModel.call(new Prompt(message, ChatOptionsBuilder.builder()
                        .withTemperature(1.3)
                        .build()))
                .getResult().getOutput().getContent();
    }

    @GetMapping("/chat/mistral-ai-options")
    String chatWithMistralAiOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return chatModel.call(new Prompt(message, MistralAiChatOptions.builder()
                        .withMaxTokens(1500)
                        .withStop(List.of("this-is-the-end", "addio"))
                        .withTemperature(0.7)
                        .withTopP(0.1)
                        .build()))
                .getResult().getOutput().getContent();
    }

    @GetMapping("/chat/functions")
    String chatWithFunctions(@RequestParam(defaultValue = "Philip Pullman") String author) {
        return chatModel.call(new Prompt("What books written by %s are available to read and what is their bestseller?".formatted(author),
                        MistralAiChatOptions.builder()
                        .withTemperature(0.3)
                        .withFunctions(Set.of("booksByAuthor", "bestsellerBookByAuthor"))
                        .build()))
                .getResult().getOutput().getContent();
    }

}
