package com.thomasvitale.ai.spring.model;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Chat examples using the low-level ChatModel API.
 */
@RestController
@RequestMapping("/model")
class ChatModelController {

    private final ChatModel chatModel;

    ChatModelController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/chat")
    String chat(@RequestParam String question) {
        return chatModel.call(question);
    }

    @GetMapping("/chat/generic-options")
    String chatWithGenericOptions(@RequestParam String question) {
        return chatModel.call(new Prompt(question, ChatOptionsBuilder.builder()
                        .withModel("llama3.2:1b")
                        .withTemperature(0.9)
                        .build()))
                .getResult().getOutput().getContent();
    }

    @GetMapping("/chat/provider-options")
    String chatWithProviderOptions(@RequestParam String question) {
        return chatModel.call(new Prompt(question, OllamaOptions.builder()
                        .withRepeatPenalty(1.5)
                        .build()))
                .getResult().getOutput().getContent();
    }

    @GetMapping("/chat/huggingface")
    String chatWithHuggingFace(@RequestParam String question) {
        return chatModel.call(new Prompt(question, ChatOptionsBuilder.builder()
                        .withModel("hf.co/SanctumAI/Llama-3.2-1B-Instruct-GGUF")
                        .build()))
                .getResult().getOutput().getContent();
    }

    @GetMapping("/chat/stream")
    Flux<String> chatStream(@RequestParam String question) {
        return chatModel.stream(question);
    }

}
