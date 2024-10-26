package com.thomasvitale.ai.spring.rag.retrieval.source;

import java.util.List;
import java.util.function.Function;

import com.thomasvitale.ai.spring.rag.Query;
import org.springframework.ai.document.Document;

/**
 * API for retrieving {@link Document}s from an underlying data source.
 */
public interface DocumentRetriever extends Function<Query, List<Document>> {

    /**
     * Retrieves {@link Document}s from an underlying data source using the given
     * {@link Query}.
     */
    List<Document> retrieve(Query query);

    /**
     * Retrieves {@link Document}s from an underlying data source using the given query
     * string.
     */
    default List<Document> retrieve(String query) {
        return retrieve(new Query(query));
    }

    /**
     * Retrieves {@link Document}s from an underlying data source using the given
     * {@link Query}.
     */
    default List<Document> apply(Query query) {
        return retrieve(query);
    }

}

