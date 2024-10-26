package com.thomasvitale.ai.spring.rag.postretrieval.document.selection;

import com.thomasvitale.ai.spring.rag.Query;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.function.BiFunction;

/**
 * A component that selects a subset of the retrieved documents based on a specific strategy.
 */
public interface DocumentSelector extends BiFunction<Query,List<Document>,List<Document>> {

    List<Document> select(Query query, List<Document> documents);

    default List<Document> apply(Query query, List<Document> documents) {
        return select(query, documents);
    }

}
