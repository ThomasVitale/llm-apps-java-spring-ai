package com.thomasvitale.ai.spring.model;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    String chat(String question) {
        return chatModel.call(question);
    }

    @GetMapping("/chat/generic-options")
    String chatGenericOptions(String question) {
        return chatModel.call(new Prompt(question, ChatOptions.builder()
                        .model("llama3.2:1b")
                        .temperature(0.9)
                        .build()))
                .getResult().getOutput().getText();
    }

    @GetMapping("/chat/provider-options")
    String chatProviderOptions(String question) {
        return chatModel.call(new Prompt(question, OllamaOptions.builder()
                        .withRepeatPenalty(1.5)
                        .build()))
                .getResult().getOutput().getText();
    }

    @GetMapping("/chat/huggingface")
    String chatHuggingFace(String question) {
        return chatModel.call(new Prompt(question, ChatOptions.builder()
                        .model("hf.co/SanctumAI/Llama-3.2-1B-Instruct-GGUF")
                        .build()))
                .getResult().getOutput().getText();
    }

    @GetMapping("/chat/stream")
    Flux<String> chatStream(String question) {
        return chatModel.stream(question);
    }

}
