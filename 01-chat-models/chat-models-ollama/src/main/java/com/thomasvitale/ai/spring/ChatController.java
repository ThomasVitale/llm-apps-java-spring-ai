package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChatController {

    private final ChatModel chatModel;

    ChatController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/chat")
    String chat(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return chatModel.call(message);
    }

    @GetMapping("/chat/generic-options")
    String chatWithGenericOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return chatModel.call(new Prompt(message, ChatOptionsBuilder.builder()
                        .withTemperature(1.3f)
                        .build()))
                .getResult().getOutput().getContent();
    }

    @GetMapping("/chat/ollama-options")
    String chatWithOllamaOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return chatModel.call(new Prompt(message, OllamaOptions.create()
                        .withModel("mistral")
                        .withRepeatPenalty(1.5f)))
                .getResult().getOutput().getContent();
    }

}
