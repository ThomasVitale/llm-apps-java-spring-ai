package com.thomasvitale.ai.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private final ChatService chatService;

    ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/function")
    String chat(@RequestParam(defaultValue = "J.R.R. Tolkien") String authorName) {
        return chatService.getAvailableBooksBy(authorName);
    }

}
