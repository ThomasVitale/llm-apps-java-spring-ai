package com.thomasvitale.ai.spring;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChatController {

    private final ChatService chatService;

    ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/ai/chat/user")
    String chatWithUserMessageTemplate(@RequestBody MusicQuestion question) {
        return chatService.chatWithUserMessageTemplate(question).getContent();
    }

    @PostMapping("/ai/chat/system")
    String chatWithSystemMessageTemplate(@RequestBody String question) {
        return chatService.chatWithSystemMessageTemplate(question).getContent();
    }

    @PostMapping("/ai/chat/external")
    String chatWithSystemMessageTemplateExternal(@RequestBody String question) {
        return chatService.chatWithSystemMessageTemplateExternal(question).getContent();
    }

}
