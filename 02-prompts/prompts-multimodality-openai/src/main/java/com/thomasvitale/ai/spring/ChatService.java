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

    ChatService(ChatClient chatClient, @Value("classpath:tabby-cat.png") Resource image) {
        this.chatClient = chatClient;
        this.image = image;
    }

    String chatFromImageFile(String message) throws IOException {
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(message)
                        .media(MimeTypeUtils.IMAGE_PNG, image)
                )
                .call()
                .content();
    }

    String chatFromImageUrl(String message) {
        var imageUrl = "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png";
        var userMessage = new UserMessage(message,
                List.of(new Media(MimeTypeUtils.IMAGE_PNG, imageUrl)));
        return chatClient.prompt()
                .messages(userMessage)
                .call()
                .content();
    }

}
