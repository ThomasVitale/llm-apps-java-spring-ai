package com.thomasvitale.ai.spring.rag.preretrieval.query.transformation;

import com.thomasvitale.ai.spring.rag.Query;
import org.springframework.util.Assert;

/**
 * A transformer that keeps the query as is.
 */
public class IdentityQueryTransformer implements QueryTransformer {

    @Override
    public Query transform(Query query) {
        Assert.notNull(query, "query cannot be null");
        return query;
    }

}
