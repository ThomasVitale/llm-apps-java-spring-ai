package com.thomasvitale.ai.spring;

import com.thomasvitale.ai.spring.advisor.RetrievalAugmentationAdvisor;
import com.thomasvitale.ai.spring.rag.retrieval.source.VectorStoreDocumentRetriever;
import com.thomasvitale.ai.spring.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagControllerTranslation {

    private final ChatClient chatClient;

    public RagControllerTranslation(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        var documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .similarityThreshold(0.50)
                .build();

        var queryTransformer = TranslationQueryTransformer.builder()
                .chatClientBuilder(chatClientBuilder.build().mutate())
                .targetLanguage("english")
                .build();

        this.chatClient = chatClientBuilder
                .defaultAdvisors(RetrievalAugmentationAdvisor.builder()
                        .documentRetriever(documentRetriever)
                        .queryTransformer(queryTransformer)
                        .build())
                .build();
    }

    @PostMapping("/rag/translation")
    String rag(@RequestBody String input) {
        return chatClient.prompt(input)
                .call()
                .content();
    }

}
