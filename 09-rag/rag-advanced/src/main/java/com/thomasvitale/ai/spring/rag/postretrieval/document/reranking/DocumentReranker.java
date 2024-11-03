package com.thomasvitale.ai.spring.rag.postretrieval.document.reranking;

import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;

import java.util.List;
import java.util.function.BiFunction;

/**
 * A component that reranks the retrieved documents based on a specific strategy.
 */
public interface DocumentReranker extends BiFunction<Query,List<Document>,List<Document>> {

    List<Document> rerank(Query query, List<Document> documents);

    default List<Document> apply(Query query, List<Document> documents) {
        return rerank(query, documents);
    }

}
