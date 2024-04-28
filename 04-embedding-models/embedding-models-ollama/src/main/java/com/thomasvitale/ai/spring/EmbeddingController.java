package com.thomasvitale.ai.spring;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class EmbeddingController {

    private final EmbeddingClient embeddingClient;

    EmbeddingController(EmbeddingClient embeddingClient) {
        this.embeddingClient = embeddingClient;
    }

    @GetMapping("/embed")
    String embed(@RequestParam(defaultValue = "And Gandalf yelled: 'You shall not pass!'") String message) {
        var embeddings = embeddingClient.embed(message);
        return "Size of the embedding vector: " + embeddings.size();
    }

    @GetMapping("/embed/ollama-options")
    String embedWithOllamaOptions(@RequestParam(defaultValue = "And Gandalf yelled: 'You shall not pass!'") String message) {
        var embeddings = embeddingClient.call(new EmbeddingRequest(List.of(message), OllamaOptions.create()
                        .withModel("llama3")))
                .getResult().getOutput();
        return "Size of the embedding vector: " + embeddings.size();
    }

}
