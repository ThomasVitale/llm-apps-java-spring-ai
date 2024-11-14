package com.thomasvitale.ai.spring.rag.retrieval.fusion;

import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * A component that combines documents retrieved from multiple data sources into a single collection of documents.
 */
public interface DocumentFuser extends Function<Map<Query, List<List<Document>>>, List<Document>> {

    List<Document> fuse(Map<Query, List<List<Document>>> documentsForQuery);

    default List<Document> apply(Map<Query, List<List<Document>>> documentsForQuery) {
        return fuse(documentsForQuery);
    }

}
