package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;

@Service
class ChatService {

    private final ChatClient chatClient;

    private final Resource image;

    ChatService(ChatClient.Builder chatClientBuilder, @Value("classpath:tabby-cat.png") Resource image) {
        this.chatClient = chatClientBuilder.build();
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

}
