package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private final ChatClient chatClient;

    @Value("classpath:tabby-cat.png")
    private Resource image;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat/image/file")
    String chatFromImageFile(String question) {
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(question)
                        .media(MimeTypeUtils.IMAGE_PNG, image)
                )
                .call()
                .content();
    }

}
