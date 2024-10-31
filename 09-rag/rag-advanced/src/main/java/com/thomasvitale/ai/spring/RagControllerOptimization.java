package com.thomasvitale.ai.spring;

import com.thomasvitale.ai.spring.advisor.RetrievalAugmentationAdvisor;
import com.thomasvitale.ai.spring.rag.preretrieval.query.expansion.MultiQueryExpander;
import com.thomasvitale.ai.spring.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.retrieval.source.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagControllerOptimization {

    private final ChatClient chatClient;

    public RagControllerOptimization(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        var documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .similarityThreshold(0.50)
                .build();

        var queryTransformer = TranslationQueryTransformer.builder()
                .chatClientBuilder(chatClientBuilder.build().mutate())
                .targetLanguage("english")
                .build();

        var queryExpander = MultiQueryExpander.builder()
                .chatClientBuilder(chatClientBuilder.build().mutate())
                .build();

        this.chatClient = chatClientBuilder
                .defaultAdvisors(RetrievalAugmentationAdvisor.builder()
                        .documentRetriever(documentRetriever)
                        .queryTransformer(queryTransformer)
                        .queryExpander(queryExpander)
                        .build())
                .build();
    }

    @PostMapping("/rag/optimization")
    String rag(@RequestBody String input) {
        return chatClient.prompt(input)
                .call()
                .content();
    }

}
