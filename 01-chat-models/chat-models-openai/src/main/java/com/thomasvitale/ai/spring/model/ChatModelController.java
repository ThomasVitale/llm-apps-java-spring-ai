package com.thomasvitale.ai.spring.model;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
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
    String chat(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return chatModel.call(question);
    }

    @GetMapping("/chat/generic-options")
    String chatWithGenericOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return chatModel.call(new Prompt(question, ChatOptionsBuilder.builder()
                        .withTemperature(0.9f)
                        .build()))
                .getResult().getOutput().getContent();
    }

    @GetMapping("/chat/provider-options")
    String chatWithProviderOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return chatModel.call(new Prompt(question, OpenAiChatOptions.builder()
                        .withModel("gpt-4o-mini")
                        .withTemperature(0.9f)
                        .withUser("jon.snow")
                        .build()))
                .getResult().getOutput().getContent();
    }

    @GetMapping("/chat/stream")
    Flux<String> chatStream(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return chatModel.stream(question);
    }

}
