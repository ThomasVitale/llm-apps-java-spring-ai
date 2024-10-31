package com.thomasvitale.ai.spring.rag.augmentation;

import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Component for augmenting a query with contextual data based on a specific strategy.
 */
public interface QueryAugmentor extends BiFunction<Query, List<Document>, Query> {

    /**
     * Augments the user query with contextual data.
     * @param query The user query to augment
     * @param documents The contextual data to use for augmentation
     * @return The augmented query
     */
    Query augment(Query query, List<Document> documents);

    /**
     * Augments the user query with contextual data.
     * @param query The user query to augment
     * @param documents The contextual data to use for augmentation
     * @return The augmented query
     */
    default Query apply(Query query, List<Document> documents) {
        return augment(query, documents);
    }

}
