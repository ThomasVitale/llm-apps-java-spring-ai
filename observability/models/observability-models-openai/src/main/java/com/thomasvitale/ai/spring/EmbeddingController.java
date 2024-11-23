package com.thomasvitale.ai.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingOptionsBuilder;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class EmbeddingController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final EmbeddingModel embeddingModel;

    EmbeddingController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/embed")
    String embed(String query) {
        logger.info("Embedding: {}", query);
        var embeddings = embeddingModel.embed(query);
        return "Size of the embedding vector: " + embeddings.length;
    }

    @GetMapping("/embed/generic-options")
    String embedGenericOptions(String query) {
        logger.info("Embedding with generic options: {}", query);
        var embeddings = embeddingModel.call(new EmbeddingRequest(List.of(query), EmbeddingOptionsBuilder.builder()
                        .withModel(OpenAiApi.EmbeddingModel.TEXT_EMBEDDING_3_SMALL.getValue())
                        .build()))
                .getResult().getOutput();
        return "Size of the embedding vector: " + embeddings.length;
    }

    @GetMapping("/embed/provider-options")
    String embedProviderOptions(String query) {
        logger.info("Embedding with provider options: {}", query);
        var embeddings = embeddingModel.call(new EmbeddingRequest(List.of(query), OpenAiEmbeddingOptions.builder()
                        .withEncodingFormat("float")
                        .build()))
                .getResult().getOutput();
        return "Size of the embedding vector: " + embeddings.length;
    }

}
