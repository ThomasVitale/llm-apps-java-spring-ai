package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private final ChatClient chatClient;

    ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/chat/simple")
    String chatText(@RequestBody String question) {
        return chatClient
                .prompt(question)
                .call()
                .content();
    }

    @PostMapping("/chat/prompt")
    String chatPrompt(@RequestBody String question) {
        var chatResponse = chatClient
                .prompt(new Prompt(question))
                .call()
                .chatResponse();
        return chatResponse.getResult().getOutput().getText();
    }

    @PostMapping("/chat/full")
    ChatResponse chatFullResponse(@RequestBody String question) {
        return chatClient
                .prompt(question)
                .call()
                .chatResponse();
    }

}
