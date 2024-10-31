package com.thomasvitale.ai.spring;

import com.thomasvitale.ai.spring.advisor.RetrievalAugmentationAdvisor;
import com.thomasvitale.ai.spring.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.retrieval.source.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagControllerMultiQuery {

    private final ChatClient chatClient;

    public RagControllerMultiQuery(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        var documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .similarityThreshold(0.50)
                .build();

        var queryExpander = MultiQueryExpander.builder()
                .chatClientBuilder(chatClientBuilder.build().mutate())
                .build();

        this.chatClient = chatClientBuilder
                .defaultAdvisors(RetrievalAugmentationAdvisor.builder()
                        .documentRetriever(documentRetriever)
                        .queryExpander(queryExpander)
                        .build())
                .build();
    }

    @PostMapping("/rag/multi-query")
    String rag(@RequestBody String input) {
        return chatClient.prompt(input)
                .call()
                .content();
    }

}
