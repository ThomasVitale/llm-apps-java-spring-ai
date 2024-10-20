package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.ollama.management.PullModelStrategy;
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
        return chatClient
                .prompt(question)
                .call()
                .content();
    }

    @GetMapping("/chat/generic-options")
    String chatWithGenericOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return chatClient
                .prompt(question)
                .options(ChatOptionsBuilder.builder()
                        .withTemperature(0.9)
                        .build())
                .call()
                .content();
    }

    @GetMapping("/chat/provider-options")
    String chatWithProviderOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return chatClient
                .prompt(question)
                .options(OllamaOptions.create()
                        .withModel("llama3.2:1b")
                        .withRepeatPenalty(1.5))
                .call()
                .content();
    }

    @GetMapping("/chat/huggingface")
    String chatWithHuggingFace(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return chatClient
                .prompt(question)
                .options(OllamaOptions.create()
                        .withModel("hf.co/SanctumAI/Llama-3.2-1B-Instruct-GGUF")
                        .withPullModelStrategy(PullModelStrategy.WHEN_MISSING)
                        .build())
                .call()
                .content();
    }

    @GetMapping("/chat/stream")
    Flux<String> chatStream(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return chatClient
                .prompt(question)
                .stream()
                .content();
    }

}
