package com.thomasvitale.ai.spring.rag.injector;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.document.Document;

import java.util.List;

/**
 * Component for injecting documents into user messages as part of a Retrieval-Augmented Generation (RAG) workflow.
 * This interface defines methods for combining user messages with relevant documents to provide context-aware responses.
 */
public interface DocumentInjector {

    UserMessage inject(UserMessage userMessage, List<Document> documents);

    default UserMessage inject(String userMessage, List<Document> documents) {
        return inject(new UserMessage(userMessage), documents);
    }

}
