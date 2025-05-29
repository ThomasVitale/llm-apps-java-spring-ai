package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.retrieval.join.ConcatenationDocumentJoiner;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagControllerMultiQuery {

    private final ChatClient chatClient;
    private final RetrievalAugmentationAdvisor retrievalAugmentationAdvisor;

    public RagControllerMultiQuery(ChatClient.Builder chatClientBuilder, TaskExecutor taskExecutor, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder.build();

        var documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .similarityThreshold(0.50)
                .topK(3)
                .build();

        var queryExpander = MultiQueryExpander.builder()
                .chatClientBuilder(chatClientBuilder.clone())
                .numberOfQueries(3)
                .build();

        this.retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(documentRetriever)
                .queryExpander(queryExpander)
                .documentJoiner(new ConcatenationDocumentJoiner())
                .taskExecutor(taskExecutor)
                .build();
    }

    @PostMapping("/rag/query/multi-query")
    String rag(@RequestBody String input) {
        return chatClient.prompt()
                .advisors(retrievalAugmentationAdvisor)
                .user(input)
                .call()
                .content();
    }

}
