package com.thomasvitale.ai.spring.rag.preretrieval.query.transformation;

import org.springframework.ai.rag.Query;

import java.util.function.Function;

/**
 * A component that transforms a query based on a specific strategy.
 */
public interface QueryTransformer extends Function<Query, Query> {

    Query transform(Query query);

    default Query apply(Query query) {
        return transform(query);
    }

}
