package com.thomasvitale.ai.spring.model;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
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
        return chatModel.call(new Prompt(question, ChatOptionsBuilder.builder()
                        .withModel(OpenAiApi.ChatModel.GPT_4_O_MINI.getValue())
                        .withTemperature(0.9)
                        .build()))
                .getResult().getOutput().getContent();
    }

    @GetMapping("/chat/provider-options")
    String chatProviderOptions(String question) {
        return chatModel.call(new Prompt(question, OpenAiChatOptions.builder()
                        .withLogprobs(true)
                        .build()))
                .getResult().getOutput().getContent();
    }

    @GetMapping("/chat/stream")
    Flux<String> chatStream(String question) {
        return chatModel.stream(question);
    }

}
