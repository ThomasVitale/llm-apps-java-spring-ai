package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.document.DefaultContentFormatter;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.transformer.KeywordMetadataEnricher;
import org.springframework.ai.transformer.SummaryMetadataEnricher;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

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
    KeywordMetadataEnricher keywordMetadataEnricher(ChatClient chatClient) {
        return new KeywordMetadataEnricher(chatClient, 3);
    }

    @Bean
    SummaryMetadataEnricher summaryMetadataEnricher(ChatClient chatClient) {
        return new SummaryMetadataEnricher(chatClient, List.of(
                SummaryMetadataEnricher.SummaryType.PREVIOUS,
                SummaryMetadataEnricher.SummaryType.CURRENT,
                SummaryMetadataEnricher.SummaryType.NEXT));
    }

    @Bean
    SimpleVectorStore documentWriter(EmbeddingClient embeddingClient) {
        return new SimpleVectorStore(embeddingClient);
    }

    public static void main(String[] args) {
        SpringApplication.run(DocumentTransformersMetadataOllamaApplication.class, args);
    }

}
