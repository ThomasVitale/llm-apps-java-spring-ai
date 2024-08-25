package com.thomasvitale.ai.spring.model;

import com.thomasvitale.ai.spring.MusicQuestion;
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

    @PostMapping("/chat/user")
    String chatWithUserMessageTemplate(@RequestBody MusicQuestion question) {
        return chatModelService.chatWithUserMessageTemplate(question);
    }

    @PostMapping("/chat/system")
    String chatWithSystemMessageTemplate(@RequestBody String question) {
        return chatModelService.chatWithSystemMessageTemplate(question);
    }

    @PostMapping("/chat/external")
    String chatWithSystemMessageTemplateExternal(@RequestBody String question) {
        return chatModelService.chatWithSystemMessageTemplateExternal(question);
    }

}
