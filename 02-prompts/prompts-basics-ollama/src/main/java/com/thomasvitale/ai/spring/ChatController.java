package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/chat/simple")
    String chatWithText(@RequestBody String question) {
        return chatService.chatWithText(question);
    }

    @PostMapping("/chat/prompt")
    String chatWithPrompt(@RequestBody String question) {
        return chatService.chatWithPrompt(question).getResult().getOutput().getContent();
    }

    @PostMapping("/chat/full")
    ChatResponse chatWithPromptAndFullResponse(@RequestBody String question) {
        return chatService.chatWithPrompt(question);
    }

}
