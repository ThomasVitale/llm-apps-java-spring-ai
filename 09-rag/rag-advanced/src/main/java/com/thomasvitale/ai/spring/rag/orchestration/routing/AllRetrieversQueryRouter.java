package com.thomasvitale.ai.spring.rag.orchestration.routing;

import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * A router that routes a query to all document retrievers.
 */
public class AllRetrieversQueryRouter implements QueryRouter {

    private final List<DocumentRetriever> documentRetrievers;

    public AllRetrieversQueryRouter(List<DocumentRetriever> documentRetrievers) {
        Assert.notEmpty(documentRetrievers, "documentRetrievers cannot be null or empty");
        this.documentRetrievers = documentRetrievers;
    }

    @Override
    public List<DocumentRetriever> route(Query query) {
        return documentRetrievers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<DocumentRetriever> documentRetrievers;

        private Builder() {}

        public Builder documentRetrievers(DocumentRetriever... documentRetrievers) {
            this.documentRetrievers = Arrays.asList(documentRetrievers);
            return this;
        }

        public Builder documentRetrievers(List<DocumentRetriever> documentRetrievers) {
            this.documentRetrievers = documentRetrievers;
            return this;
        }

        public AllRetrieversQueryRouter build() {
            return new AllRetrieversQueryRouter(documentRetrievers);
        }
    }

}
