package com.thomasvitale.ai.spring;

import org.springframework.ai.document.DefaultContentFormatter;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DocumentTransformersSplittersOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentTransformersSplittersOllamaApplication.class, args);
    }

    @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return new SimpleVectorStore(embeddingModel);
    }

    @Bean
    DefaultContentFormatter defaultContentFormatter() {
        return DefaultContentFormatter.builder()
                .withExcludedEmbedMetadataKeys("NewEmbedKey")
                .withExcludedInferenceMetadataKeys("NewInferenceKey")
                .build();
    }

}
