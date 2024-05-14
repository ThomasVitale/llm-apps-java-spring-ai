package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.util.List;

@Service
class ChatService {

    private final ChatClient chatClient;

    private final Resource image;

    private final Resource audio;

    ChatService(ChatClient chatClient, @Value("classpath:tabby-cat.png") Resource image, @Value("classpath:speech.mp3") Resource audio) {
        this.chatClient = chatClient;
        this.image = image;
        this.audio = audio;
    }

    String chatFromImageFile(String message) throws IOException {
        var imageData = image.getContentAsByteArray();
        var userMessage = new UserMessage(message,
                List.of(new Media(MimeTypeUtils.IMAGE_PNG, imageData)));
        return chatClient.call(userMessage);
    }

    String chatFromImageUrl(String message) {
        var imageUrl = "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png";
        var userMessage = new UserMessage(message,
                List.of(new Media(MimeTypeUtils.IMAGE_PNG, imageUrl)));
        return chatClient.call(userMessage);
    }

    String chatFromAudioFile(String message) throws IOException {
        var audioData = audio.getContentAsByteArray();
        var userMessage = new UserMessage(message,
                List.of(new Media(MimeTypeUtils.parseMimeType("audio/mpeg"), audioData)));
        return chatClient.call(userMessage);
    }

}
