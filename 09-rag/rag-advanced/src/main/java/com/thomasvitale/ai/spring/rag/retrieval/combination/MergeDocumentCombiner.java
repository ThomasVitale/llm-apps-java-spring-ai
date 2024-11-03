package com.thomasvitale.ai.spring.rag.retrieval.combination;

import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A combiner that merges together all documents retrieved for all queries.
 */
public class MergeDocumentCombiner implements DocumentCombiner {

    @Override
    public List<Document> combine(Map<Query, List<List<Document>>> documentsForQuery) {
        return new ArrayList<>(documentsForQuery.values().stream()
                .flatMap(List::stream)
                .flatMap(List::stream)
                .collect(Collectors.toMap(Document::getId, Function.identity(), (existing, duplicate) -> existing))
                .values());
    }

}
