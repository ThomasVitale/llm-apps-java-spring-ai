package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChatController {

    private final ChatClient chatClient;

    ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    String chat(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return chatClient.call(message);
    }

    @GetMapping("/chat/generic-options")
    String chatWithGenericOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return chatClient.call(new Prompt(message, ChatOptionsBuilder.builder()
                        .withTemperature(0.9f)
                        .build()))
                .getResult().getOutput().getContent();
    }

    @GetMapping("/chat/mistral-ai-options")
    String chatWithMistralAiOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return chatClient.call(new Prompt(message, MistralAiChatOptions.builder()
                        .withModel("open-mixtral-8x7b")
                        .withSafePrompt(true)
                        .build()))
                .getResult().getOutput().getContent();
    }

}
