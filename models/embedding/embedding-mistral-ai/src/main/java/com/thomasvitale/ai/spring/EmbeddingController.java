package com.thomasvitale.ai.spring;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingOptions;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.mistralai.MistralAiEmbeddingOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class EmbeddingController {

    private final EmbeddingModel embeddingModel;

    EmbeddingController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/embed")
    String embed(String query) {
        var embeddings = embeddingModel.embed(query);
        return "Size of the embedding vector: " + embeddings.length;
    }

    @GetMapping("/embed/generic-options")
    String embedGenericOptions(String query) {
        var embeddings = embeddingModel.call(new EmbeddingRequest(List.of(query), EmbeddingOptions.builder()
                        .withModel(MistralAiApi.EmbeddingModel.EMBED.getValue())
                        .build()))
                .getResult().getOutput();
        return "Size of the embedding vector: " + embeddings.length;
    }

    @GetMapping("/embed/provider-options")
    String embedProviderOptions(String query) {
        var embeddings = embeddingModel.call(new EmbeddingRequest(List.of(query), MistralAiEmbeddingOptions.builder()
                        .withEncodingFormat("float")
                        .build()))
                .getResult().getOutput();
        return "Size of the embedding vector: " + embeddings.length;
    }

}
