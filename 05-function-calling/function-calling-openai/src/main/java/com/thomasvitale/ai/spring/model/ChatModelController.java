package com.thomasvitale.ai.spring.model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Chat examples using the low-level ChatModel API.
 */
@RestController
@RequestMapping("/model")
class ChatModelController {

    private final ChatModelService chatService;

    ChatModelController(ChatModelService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/function")
    String chat(@RequestParam(defaultValue = "J.R.R. Tolkien") String authorName) {
        return chatService.getAvailableBooksBy(authorName);
    }

    @GetMapping("/chat/function/explicit")
    String chatVariant(@RequestParam(defaultValue = "J.R.R. Tolkien") String authorName) {
        return chatService.getAvailableBooksByWithExplicitFunction(authorName);
    }

}
