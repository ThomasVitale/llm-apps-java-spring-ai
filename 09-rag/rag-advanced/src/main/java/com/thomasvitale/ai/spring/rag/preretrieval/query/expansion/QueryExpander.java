package com.thomasvitale.ai.spring.rag.preretrieval.query.expansion;

import com.thomasvitale.ai.spring.rag.Query;

import java.util.List;
import java.util.function.Function;

/**
 * A component that expands a query to one or more queries based on a specific strategy.
 */
public interface QueryExpander extends Function<Query, List<Query>> {

    List<Query> expand(Query query);

    default List<Query> apply(Query query) {
        return expand(query);
    }

}
