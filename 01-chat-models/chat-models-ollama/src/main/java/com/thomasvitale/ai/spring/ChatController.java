package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChatController {

    private final ChatClient chatClient;

    ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ai/chat")
    String chat(@RequestParam(defaultValue = "What does Gandalf say to the Balrog?") String message) {
        return chatClient.generate(message);
    }

}
