package com.thomasvitale.ai.spring;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChatbotController {

    private final ChatbotService chatbotService;

    ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/chat/{chatId}")
    String chat(@PathVariable String chatId, @RequestBody String input) {
        return chatbotService.chat(chatId, input);
    }

}
