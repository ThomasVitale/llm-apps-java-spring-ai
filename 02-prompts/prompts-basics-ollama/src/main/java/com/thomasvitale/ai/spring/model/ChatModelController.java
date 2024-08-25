package com.thomasvitale.ai.spring.model;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Chat examples using the low-level ChatModel API.
 */
@RestController
@RequestMapping("/model")
class ChatModelController {

    private final ChatModelService chatModelService;

    ChatModelController(ChatModelService chatModelService) {
        this.chatModelService = chatModelService;
    }

    @PostMapping("/chat/simple")
    String chatWithText(@RequestBody String question) {
        return chatModelService.chatWithText(question);
    }

    @PostMapping("/chat/prompt")
    String chatWithPrompt(@RequestBody String question) {
        return chatModelService.chatWithPrompt(question).getResult().getOutput().getContent();
    }

    @PostMapping("/chat/full")
    ChatResponse chatWithPromptAndFullResponse(@RequestBody String question) {
        return chatModelService.chatWithPrompt(question);
    }

}
