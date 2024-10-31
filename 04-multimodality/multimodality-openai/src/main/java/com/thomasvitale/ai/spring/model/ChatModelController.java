package com.thomasvitale.ai.spring.model;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URI;

/**
 * Chat examples using the low-level ChatModel API.
 */
@RestController
@RequestMapping("/model")
class ChatModelController {

    private final ChatModel chatModel;

    @Value("classpath:tabby-cat.png")
    private Resource image;

    ChatModelController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/chat/image/file")
    String chatImageFile(String question) {
        var userMessage = new UserMessage(question, new Media(MimeTypeUtils.IMAGE_PNG, image));
        var prompt = new Prompt(userMessage);
        var chatResponse = chatModel.call(prompt);
        return chatResponse.getResult().getOutput().getContent();
    }

    @GetMapping("/chat/image/url")
    String chatImageUrl(String question) throws MalformedURLException {
        var imageUrl = "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png";
        var url = URI.create(imageUrl).toURL();

        var userMessage = new UserMessage(question, new Media(MimeTypeUtils.IMAGE_PNG, url));
        var prompt = new Prompt(userMessage);
        var chatResponse = chatModel.call(prompt);
        return chatResponse.getResult().getOutput().getContent();
    }

}
