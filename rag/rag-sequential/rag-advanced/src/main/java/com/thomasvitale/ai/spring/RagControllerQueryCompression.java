package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.CompressionQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagControllerQueryCompression {

    private final ChatClient chatClient;
    private final MessageChatMemoryAdvisor chatMemoryAdvisor;
    private final RetrievalAugmentationAdvisor retrievalAugmentationAdvisor;

    public RagControllerQueryCompression(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder.build();

        this.chatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
                .build();

        var documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .similarityThreshold(0.50)
                .topK(3)
                .build();

        var queryTransformer = CompressionQueryTransformer.builder()
                .chatClientBuilder(chatClientBuilder.clone())
                .build();

        this.retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(documentRetriever)
                .queryTransformers(queryTransformer)
                .build();
    }

    @PostMapping("/rag/query/compression/{conversationId}")
    String rag(@RequestBody String input, @PathVariable String conversationId) {
        return chatClient.prompt()
                .advisors(chatMemoryAdvisor, retrievalAugmentationAdvisor)
                .advisors(advisors -> advisors.param(
                        ChatMemory.CONVERSATION_ID, conversationId))
                .user(input)
                .call()
                .content();
    }

}
