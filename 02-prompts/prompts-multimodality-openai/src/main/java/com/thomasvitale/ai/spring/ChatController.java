package com.thomasvitale.ai.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
class ChatController {

    private final ChatService chatService;

    ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/image/file")
    String chatFromImageFile(@RequestParam(defaultValue = "What do you see in this picture? Give a short answer") String message) throws IOException {
        return chatService.chatFromImageFile(message);
    }

    @GetMapping("/chat/image/url")
    String chatFromImageUrl(@RequestParam(defaultValue = "What do you see in this picture? Give a short answer") String message) {
        return chatService.chatFromImageUrl(message);
    }

    @GetMapping("/chat/audio/file")
    String chatFromAudioFile(@RequestParam(defaultValue = "Who's the artist who sang this song? Give a short answer") String message) throws IOException {
        return chatService.chatFromAudioFile(message);
    }

}
