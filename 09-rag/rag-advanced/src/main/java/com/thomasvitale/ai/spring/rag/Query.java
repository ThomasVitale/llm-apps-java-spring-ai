package com.thomasvitale.ai.spring.rag;

import org.springframework.util.Assert;

/**
 * Represents a query in the context of a Retrieval Augmented Generation (RAG) flow.
 */
public record Query(String text) {
    public Query {
        Assert.hasText(text, "text cannot be null or empty");
    }
}
