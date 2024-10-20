package com.thomasvitale.ai.spring;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingOptionsBuilder;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.ollama.api.OllamaModel;
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
        return "Size of the embedding vector: " + embeddings.length;
    }

    @GetMapping("/embed/generic-options")
    String embedWithGenericOptions(@RequestParam(defaultValue = "And Gandalf yelled: 'You shall not pass!'") String message) {
        var embeddings = embeddingModel.call(new EmbeddingRequest(List.of(message), EmbeddingOptionsBuilder.builder()
                        .withModel(OllamaModel.NOMIC_EMBED_TEXT.getName())
                        .build()))
                .getResult().getOutput();
        return "Size of the embedding vector: " + embeddings.length;
    }

}
