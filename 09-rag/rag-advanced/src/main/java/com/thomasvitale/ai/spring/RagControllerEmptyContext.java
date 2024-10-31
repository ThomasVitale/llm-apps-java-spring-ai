package com.thomasvitale.ai.spring;

import com.thomasvitale.ai.spring.advisor.RetrievalAugmentationAdvisor;
import com.thomasvitale.ai.spring.rag.augmentation.ContextualQueryAugmentor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.retrieval.source.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagControllerEmptyContext {

    private final ChatClient chatClient;

    public RagControllerEmptyContext(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        var documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .similarityThreshold(0.50)
                .build();

        var queryAugmentor = ContextualQueryAugmentor.builder()
                .allowEmptyContext(false)
                .build();

        this.chatClient = chatClientBuilder
                .defaultAdvisors(RetrievalAugmentationAdvisor.builder()
                        .documentRetriever(documentRetriever)
                        .promptAugmentor(queryAugmentor)
                        .build())
                .build();
    }

    @PostMapping("/rag/empty-context")
    String rag(@RequestBody String input) {
        return chatClient.prompt(input)
                .call()
                .content();
    }

}
