package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.web.bind.annotation.GetMapping;
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
    String chat(String question) {
        return chatClient
                .prompt(question)
                .call()
                .content();
    }

    @GetMapping("/chat/generic-options")
    String chatGenericOptions(String question) {
        return chatClient
                .prompt(question)
                .options(ChatOptionsBuilder.builder()
                        .withModel("llama3.2:1b")
                        .withTemperature(0.9)
                        .build())
                .call()
                .content();
    }

    @GetMapping("/chat/provider-options")
    String chatProviderOptions(String question) {
        return chatClient
                .prompt(question)
                .options(OllamaOptions.builder()
                        .withRepeatPenalty(1.5)
                        .build())
                .call()
                .content();
    }

    @GetMapping("/chat/huggingface")
    String chatHuggingFace(String question) {
        return chatClient
                .prompt(question)
                .options(ChatOptionsBuilder.builder()
                        .withModel("hf.co/SanctumAI/Llama-3.2-1B-Instruct-GGUF")
                        .build())
                .call()
                .content();
    }

    @GetMapping("/chat/stream")
    Flux<String> chatStream(String question) {
        return chatClient
                .prompt(question)
                .stream()
                .content();
    }

}
