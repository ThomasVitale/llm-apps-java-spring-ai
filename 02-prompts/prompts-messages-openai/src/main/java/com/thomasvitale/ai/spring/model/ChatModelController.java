package com.thomasvitale.ai.spring.model;

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

    @PostMapping("/chat/single")
    String chatWithSingleMessage(@RequestBody String question) {
        return chatModelService.chatWithSingleMessage(question);
    }

    @PostMapping("/chat/multiple")
    String chatWithMultipleMessages(@RequestBody String question) {
        return chatModelService.chatWithMultipleMessages(question);
    }

    @PostMapping("/chat/external")
    String chatWithExternalMessage(@RequestBody String question) {
        return chatModelService.chatWithExternalMessage(question);
    }

}
