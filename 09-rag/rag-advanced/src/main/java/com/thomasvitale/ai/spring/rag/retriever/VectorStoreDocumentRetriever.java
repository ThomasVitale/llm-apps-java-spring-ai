package com.thomasvitale.ai.spring.rag.retriever;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentRetriever;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.List;

/**
 * A document retriever that uses a vector store to perform similarity searches.
 * It allows for filtering and configuring the similarity threshold and the number of top results.
 *
 * <p>Example usage:
 * <pre>{@code
 * VectorStore vectorStore = ...;
 * VectorStoreDocumentRetriever retriever = VectorStoreDocumentRetriever.builder()
 *     .withVectorStore(vectorStore)
 *     .withSimilarityThreshold(0.8)
 *     .withTopK(10)
 *     .withFilterExpression(filterExpression)
 *     .build();
 * List<Document> documents = retriever.retrieve("example query");
 * }</pre>
 *
 * @see VectorStore
 * @see Filter.Expression
 */
public class VectorStoreDocumentRetriever implements DocumentRetriever {

    private final VectorStore vectorStore;

    private final Double similarityThreshold;

    private final Integer topK;

    @Nullable
    private final Filter.Expression filterExpression;

    public VectorStoreDocumentRetriever(VectorStore vectorStore, @Nullable Double similarityThreshold, @Nullable Integer topK, @Nullable Filter.Expression filterExpression) {
        Assert.notNull(vectorStore, "vectorStore cannot be null");
        this.vectorStore = vectorStore;
        this.similarityThreshold = similarityThreshold != null ? similarityThreshold : SearchRequest.SIMILARITY_THRESHOLD_ACCEPT_ALL;
        this.topK = topK != null ? topK : SearchRequest.DEFAULT_TOP_K;
        this.filterExpression = filterExpression;
    }

    @Override
    public List<Document> retrieve(String query) {
        Assert.hasText(query, "query cannot be null or empty");
        return vectorStore.similaritySearch(SearchRequest.query(query)
                .withFilterExpression(filterExpression)
                .withSimilarityThreshold(similarityThreshold)
                .withTopK(topK));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private VectorStore vectorStore;
        private Double similarityThreshold;
        private Integer topK;
        private Filter.Expression filterExpression;

        private Builder() {}

        public Builder withVectorStore(VectorStore vectorStore) {
            this.vectorStore = vectorStore;
            return this;
        }

        public Builder withSimilarityThreshold(Double similarityThreshold) {
            this.similarityThreshold = similarityThreshold;
            return this;
        }

        public Builder withTopK(Integer topK) {
            this.topK = topK;
            return this;
        }

        public Builder withFilterExpression(Filter.Expression filterExpression) {
            this.filterExpression = filterExpression;
            return this;
        }

        public VectorStoreDocumentRetriever build() {
            return new VectorStoreDocumentRetriever(vectorStore, similarityThreshold, topK, filterExpression);
        }
    }

}
