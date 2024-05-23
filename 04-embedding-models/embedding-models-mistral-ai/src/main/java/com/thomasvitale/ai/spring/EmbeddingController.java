package com.thomasvitale.ai.spring;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.mistralai.MistralAiEmbeddingOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class EmbeddingController {

    private final EmbeddingModel embeddingModel;

    EmbeddingController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/embed")
    String embed(@RequestParam(defaultValue = "And Gandalf yelled: 'You shall not pass!'") String message) {
        var embeddings = embeddingModel.embed(message);
        return "Size of the embedding vector: " + embeddings.size();
    }

    @GetMapping("/embed/mistral-ai-options")
    String embedWithMistralAiOptions(@RequestParam(defaultValue = "And Gandalf yelled: 'You shall not pass!'") String message) {
        var embeddings = embeddingModel.call(new EmbeddingRequest(List.of(message), MistralAiEmbeddingOptions.builder()
                        .withModel("mistral-embed")
                        .build()))
                .getResult().getOutput();
        return "Size of the embedding vector: " + embeddings.size();
    }

}
