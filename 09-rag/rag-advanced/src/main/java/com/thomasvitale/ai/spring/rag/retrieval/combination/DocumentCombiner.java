package com.thomasvitale.ai.spring.rag.retrieval.combination;

import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * A component that combines documents retrieved from multiple data sources into a single collection of documents.
 */
public interface DocumentCombiner extends Function<Map<Query, List<List<Document>>>, List<Document>> {

    List<Document> combine(Map<Query, List<List<Document>>> documentsForQuery);

    default List<Document> apply(Map<Query, List<List<Document>>> documentsForQuery) {
        return combine(documentsForQuery);
    }

}
