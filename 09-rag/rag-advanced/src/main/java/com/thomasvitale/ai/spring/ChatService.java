package com.thomasvitale.ai.spring;

import com.thomasvitale.ai.spring.rag.RetrievalAugmentationAdvisor;
import com.thomasvitale.ai.spring.rag.retriever.VectorStoreDocumentRetriever;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.DocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
class ChatService {

    private final ChatClient chatClient;
    private final DocumentRetriever documentRetriever;

    ChatService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder.build();
        this.documentRetriever = VectorStoreDocumentRetriever.builder()
                .withVectorStore(vectorStore)
                .build();
    }

    String chatWithDocument(String message) {
        return chatClient.prompt()
                .advisors(RetrievalAugmentationAdvisor.builder()
                        .withDocumentRetriever(documentRetriever)
                        .build())
                .user(message)
                .call()
                .content();
    }

}
