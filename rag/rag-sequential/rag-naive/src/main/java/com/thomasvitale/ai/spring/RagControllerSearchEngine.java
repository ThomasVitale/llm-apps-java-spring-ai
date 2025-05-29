package com.thomasvitale.ai.spring;

import com.thomasvitale.ai.spring.components.SearchEngineDocumentRetriever;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
class RagControllerSearchEngine {

    private final ChatClient chatClient;
    private final RetrievalAugmentationAdvisor retrievalAugmentationAdvisor;

    RagControllerSearchEngine(ChatClient.Builder chatClientBuilder, RestClient.Builder restClientBuilder) {
        this.chatClient = chatClientBuilder.build();
        this.retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(SearchEngineDocumentRetriever.builder()
                        .restClientBuilder(restClientBuilder)
                        .maxResults(10)
                        .build())
                .build();
    }

    @PostMapping("/rag/search-engine")
    String chatWithDocument(@RequestBody String question) {
        return chatClient.prompt()
                .advisors(retrievalAugmentationAdvisor)
                .user(question)
                .call()
                .content();
    }

}
