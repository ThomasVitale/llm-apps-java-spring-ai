package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private final ChatClient chatClient;

    ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    String chat(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    @GetMapping("/chat/generic-options")
    String chatWithGenericOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return chatClient.prompt()
                .user(question)
                .options(ChatOptionsBuilder.builder()
                        .withTemperature(0.9f)
                        .build())
                .call()
                .content();
    }

    @GetMapping("/chat/provider-options")
    String chatWithProviderOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return chatClient.prompt()
                .user(question)
                .options(OllamaOptions.create()
                        .withModel("mistral")
                        .withRepeatPenalty(1.5f))
                .call()
                .content();
    }

    @GetMapping("/chat/stream")
    Flux<String> chatStream(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return chatClient.prompt()
                .user(question)
                .stream()
                .content();
    }

}
