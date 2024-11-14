package com.thomasvitale.ai.spring.rag.analysis.query.expansion;

import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.analysis.query.expansion.QueryExpander;

import java.util.List;

public class IdentityQueryExpander implements QueryExpander {

    @Override
    public List<Query> expand(Query query) {
        return List.of(query);
    }

}
