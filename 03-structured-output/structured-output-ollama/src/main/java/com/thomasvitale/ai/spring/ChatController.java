package com.thomasvitale.ai.spring;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private final ChatService chatService;

    ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat/bean")
    ArtistInfo chatWithBeanOutput(@RequestBody MusicQuestion question) {
        return chatService.chatWithBeanOutput(question);
    }

    @PostMapping("/chat/map")
    Map<String,Object> chatWithMapOutput(@RequestBody MusicQuestion question) {
        return chatService.chatWithMapOutput(question);
    }

    @PostMapping("/chat/list")
    List<String> chatWithListOutput(@RequestBody MusicQuestion question) {
        return chatService.chatWithListOutput(question);
    }

}
