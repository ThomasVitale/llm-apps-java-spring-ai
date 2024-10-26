package com.thomasvitale.ai.spring.rag.preretrieval.query.expansion;

import com.thomasvitale.ai.spring.rag.Query;
import org.springframework.util.Assert;

import java.util.List;

/**
 * An expander that keeps the query as is.
 */
public class IdentityQueryExpander implements QueryExpander {

    @Override
    public List<Query> expand(Query query) {
        Assert.notNull(query, "query cannot be null");
        return List.of(query);
    }

}
