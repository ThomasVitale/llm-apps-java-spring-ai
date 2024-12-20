package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URI;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private final ChatClient chatClient;

    @Value("classpath:speech.mp3")
    private Resource audio;

    @Value("classpath:tabby-cat.png")
    private Resource image;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat/image/file")
    String chatImageFile(String question) {
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(question)
                        .media(MimeTypeUtils.IMAGE_PNG, image)
                )
                .call()
                .content();
    }

    @GetMapping("/chat/image/url")
    String chatImageUrl(String question) throws MalformedURLException {
        var imageUrl = "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png";
        var url = URI.create(imageUrl).toURL();
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(question)
                        .media(new Media(MimeTypeUtils.IMAGE_PNG, url))
                )
                .call()
                .content();
    }

    @GetMapping("/chat/audio/file")
    String chatAudioFile(String question) {
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(question)
                        .media(MimeTypeUtils.parseMimeType("audio/mp3"), audio)
                )
                .options(OpenAiChatOptions.builder()
                        .model(OpenAiApi.ChatModel.GPT_4_O_AUDIO_PREVIEW.getValue())
                        .build())
                .call()
                .content();
    }

}
