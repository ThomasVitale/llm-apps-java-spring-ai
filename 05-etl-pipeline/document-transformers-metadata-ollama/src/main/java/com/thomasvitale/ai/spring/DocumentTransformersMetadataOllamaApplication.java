package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatModel;
import org.springframework.ai.document.DefaultContentFormatter;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformer.KeywordMetadataEnricher;
import org.springframework.ai.transformer.SummaryMetadataEnricher;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DocumentTransformersMetadataOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentTransformersMetadataOllamaApplication.class, args);
    }

    @Bean
    ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
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

    @Bean
    KeywordMetadataEnricher keywordMetadataEnricher(ChatModel chatModel) {
        return new KeywordMetadataEnricher(chatModel, 3);
    }

    @Bean
    SummaryMetadataEnricher summaryMetadataEnricher(ChatModel chatModel) {
        return new SummaryMetadataEnricher(chatModel, List.of(
                SummaryMetadataEnricher.SummaryType.PREVIOUS,
                SummaryMetadataEnricher.SummaryType.CURRENT,
                SummaryMetadataEnricher.SummaryType.NEXT));
    }

}
