package com.thomasvitale.ai.spring;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
class ChatController {

    private final ChatService chatService;

    ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/ai/chat/bean")
    ArtistInfo chatWithBeanOutput(@RequestBody MusicQuestion question) {
        return chatService.chatWithBeanOutput(question);
    }

    @PostMapping("/ai/chat/map")
    Map<String,Object> chatWithMapOutput(@RequestBody MusicQuestion question) {
        return chatService.chatWithMapOutput(question);
    }

    @PostMapping("/ai/chat/list")
    List<String> chatWithListOutput(@RequestBody MusicQuestion question) {
        return chatService.chatWithListOutput(question);
    }

}
