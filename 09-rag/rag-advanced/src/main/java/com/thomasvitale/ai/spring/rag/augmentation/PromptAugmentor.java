package com.thomasvitale.ai.spring.rag.augmentation;

import com.thomasvitale.ai.spring.rag.Query;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Component for augmenting a prompt with contextual information based on a specific strategy.
 */
public interface PromptAugmentor extends BiFunction<Query, List<Document>, UserMessage> {

    UserMessage augment(Query query, List<Document> documents);

    default UserMessage apply(Query query, List<Document> documents) {
        return augment(query, documents);
    }

}
