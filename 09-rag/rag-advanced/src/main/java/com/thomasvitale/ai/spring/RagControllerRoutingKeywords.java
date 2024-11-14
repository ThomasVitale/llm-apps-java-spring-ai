package com.thomasvitale.ai.spring;

import com.thomasvitale.ai.spring.advisor.RetrievalAugmentationAdvisor;
import com.thomasvitale.ai.spring.rag.orchestration.routing.QueryRouter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RagControllerRoutingKeywords {

    private final ChatClient chatClient;

    public RagControllerRoutingKeywords(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        var northPoleDocumentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .similarityThreshold(0.50)
                .filterExpression(() -> new FilterExpressionBuilder()
                        .eq("location", "North Pole")
                        .build())
                .build();

        var italyDocumentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .similarityThreshold(0.50)
                .filterExpression(new FilterExpressionBuilder()
                        .eq("location", "Italy")
                        .build())
                .build();

        QueryRouter queryRouter = query -> {
            List<DocumentRetriever> documentRetrievers = new ArrayList<>();
            if (query.text().contains("North Pole")) {
                documentRetrievers.add(northPoleDocumentRetriever);
            } else if (query.text().contains("Italy")) {
                documentRetrievers.add(italyDocumentRetriever);
            } else {
                documentRetrievers.add(northPoleDocumentRetriever);
                documentRetrievers.add(italyDocumentRetriever);
            }
            return documentRetrievers;
        };

        this.chatClient = chatClientBuilder
                .defaultAdvisors(RetrievalAugmentationAdvisor.builder()
                        .queryRouter(queryRouter)
                        .build())
                .build();
    }

    @PostMapping("/rag/routing-keywords")
    String rag(@RequestBody String input) {
        return chatClient.prompt(input)
                .call()
                .content();
    }

}
