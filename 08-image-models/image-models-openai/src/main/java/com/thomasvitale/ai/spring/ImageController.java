package com.thomasvitale.ai.spring;

import org.springframework.ai.image.ImageClient;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ImageController {

    private final ImageClient imageClient;

    ImageController(ImageClient imageClient) {
        this.imageClient = imageClient;
    }

    @GetMapping("/image")
    String image(@RequestParam(defaultValue = "Here comes the sun") String message) {
        return imageClient.call(new ImagePrompt(message)).getResult().getOutput().getUrl();
    }

    @GetMapping("/image/openai-options")
    String imageWithOpenAiOptions(@RequestParam(defaultValue = "Here comes the sun") String message) {
        var imageResponse = imageClient.call(new ImagePrompt(message, OpenAiImageOptions.builder()
                .withQuality("standard")
                .withN(1)
                .withHeight(1024)
                .withWidth(1024)
                .withModel(OpenAiImageApi.ImageModel.DALL_E_3.getValue())
                .build()));
        return imageResponse.getResult().getOutput().getUrl();
    }

}
