package com.thomasvitale.ai.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    private final ImageModel imageModel;

    ImageController(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @GetMapping("/image")
    String image(String message) {
        logger.info("Generating image: {}", message);
        var imageResponse = imageModel.call(new ImagePrompt(message, ImageOptionsBuilder.builder()
                .height(1024)
                .width(1024)
                .build()));
        return imageResponse.getResult().getOutput().getUrl();
    }

    @GetMapping("/image/provider-options")
    String imageProviderOptions(String message) {
        logger.info("Generating image with provider options: {}", message);
        var imageResponse = imageModel.call(new ImagePrompt(message, OpenAiImageOptions.builder()
                .quality("standard")
                .n(1)
                .height(1024)
                .width(1024)
                .model(com.openai.models.images.ImageModel.GPT_IMAGE_1_MINI.asString())
                .responseFormat("url")
                .build()));
        return imageResponse.getResult().getOutput().getUrl();
    }

}
