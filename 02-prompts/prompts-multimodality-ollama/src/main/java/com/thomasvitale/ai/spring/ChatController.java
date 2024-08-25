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

    @GetMapping("/chat/image/file")
    String chatFromImageFile(@RequestParam(defaultValue = "What do you see in this picture? Give a short answer") String question) {
        return chatService.chatFromImageFile(question);
    }

}
