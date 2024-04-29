package com.thomasvitale.ai.spring;

import org.springframework.ai.document.DefaultContentFormatter;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DocumentTransformersMetadataOllamaApplication {

    @Bean
    DefaultContentFormatter defaultContentFormatter() {
        return DefaultContentFormatter.builder()
                .withExcludedEmbedMetadataKeys("NewEmbedKey")
                .withExcludedInferenceMetadataKeys("NewInferenceKey")
                .build();
    }

    @Bean
    VectorStore vectorStore(EmbeddingClient embeddingClient) {
        return new SimpleVectorStore(embeddingClient);
    }

    public static void main(String[] args) {
        SpringApplication.run(DocumentTransformersMetadataOllamaApplication.class, args);
    }

}
