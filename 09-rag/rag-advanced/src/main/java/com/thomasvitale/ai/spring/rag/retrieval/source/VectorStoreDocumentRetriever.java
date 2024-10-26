package com.thomasvitale.ai.spring.rag.retrieval.source;

import java.util.List;
import java.util.function.Supplier;

import com.thomasvitale.ai.spring.rag.Query;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * A document retriever that uses a vector store to search for documents. It supports
 * filtering based on metadata, similarity threshold, and top-k results.
 *
 * <p>
 * Example usage: <pre>{@code
 * VectorStoreDocumentRetriever retriever = VectorStoreDocumentRetriever.builder()
 *     .withVectorStore(vectorStore)
 *     .withSimilarityThreshold(0.73)
 *     .withTopK(5)
 *     .withFilterExpression(filterExpression)
 *     .build();
 * List<Document> documents = retriever.retrieve("example query");
 * }</pre>
 *
 * @author Thomas Vitale
 * @since 1.0.0
 * @see VectorStore
 * @see Filter.Expression
 */
public class VectorStoreDocumentRetriever implements DocumentRetriever {

    private final VectorStore vectorStore;

    private final Double similarityThreshold;

    private final Integer topK;

    // Supplier to allow for lazy evaluation of the filter expression,
    // which may depend on the execution content. For example, you may want to
    // filter dynamically based on the current user's identity or tenant ID.
    private final Supplier<Filter.Expression> filterExpression;

    public VectorStoreDocumentRetriever(VectorStore vectorStore, @Nullable Double similarityThreshold,
                                        @Nullable Integer topK, @Nullable Supplier<Filter.Expression> filterExpression) {
        Assert.notNull(vectorStore, "vectorStore cannot be null");
        this.vectorStore = vectorStore;
        this.similarityThreshold = similarityThreshold != null ? similarityThreshold
                : SearchRequest.SIMILARITY_THRESHOLD_ACCEPT_ALL;
        this.topK = topK != null ? topK : SearchRequest.DEFAULT_TOP_K;
        this.filterExpression = filterExpression != null ? filterExpression : () -> null;
    }

    @Override
    public List<Document> retrieve(Query query) {
        Assert.notNull(query, "query cannot be null");
        var searchRequest = SearchRequest.query(query.text())
                .withFilterExpression(this.filterExpression.get())
                .withSimilarityThreshold(this.similarityThreshold)
                .withTopK(this.topK);
        return this.vectorStore.similaritySearch(searchRequest);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link VectorStoreDocumentRetriever}.
     */
    public static final class Builder {

        private VectorStore vectorStore;

        private Double similarityThreshold;

        private Integer topK;

        private Supplier<Filter.Expression> filterExpression;

        private Builder() {
        }

        public Builder vectorStore(VectorStore vectorStore) {
            this.vectorStore = vectorStore;
            return this;
        }

        public Builder similarityThreshold(Double similarityThreshold) {
            this.similarityThreshold = similarityThreshold;
            return this;
        }

        public Builder topK(Integer topK) {
            this.topK = topK;
            return this;
        }

        public Builder filterExpression(Filter.Expression filterExpression) {
            this.filterExpression = () -> filterExpression;
            return this;
        }

        public Builder filterExpression(Supplier<Filter.Expression> filterExpression) {
            this.filterExpression = filterExpression;
            return this;
        }

        public VectorStoreDocumentRetriever build() {
            return new VectorStoreDocumentRetriever(this.vectorStore, this.similarityThreshold, this.topK,
                    this.filterExpression);
        }

    }

}
