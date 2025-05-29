package com.thomasvitale.ai.spring.components;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Retrieves documents from a search engine using the Tavily API.
 *
 * @see <a href="https://tavily.com">Tavily</a>
 */
public class SearchEngineDocumentRetriever implements DocumentRetriever {

    private static final String API_KEY = "TAVILY_SEARCH_API_KEY";

    private static final int DEFAULT_MAX_RESULTS = 10;

    private final RestClient restClient;

    private final int maxResults;

    public SearchEngineDocumentRetriever(RestClient.Builder restClientBuilder, int maxResults) {
        Assert.notNull(restClientBuilder, "restClientBuilder cannot be null");
        this.restClient = restClientBuilder
                .baseUrl("https://api.tavily.com/search")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + System.getenv(API_KEY))
                .build();
        this.maxResults = maxResults;
    }

    @Override
    public List<Document> retrieve(Query query) {
        Assert.notNull(query, "query cannot be null");

        var response = restClient.post()
                .body(new TavilySearchRequest(query.text(), "basic", maxResults))
                .retrieve()
                .body(TavilySearchResponse.class);

        if (response == null || CollectionUtils.isEmpty(response.results())) {
            return List.of();
        }

        return response.results().stream()
                .map(result -> Document.builder()
                        .text(result.content())
                        .metadata("title", result.title())
                        .metadata("url", result.url())
                        .score(result.score())
                        .build())
                .toList();
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record TavilySearchRequest(String query, String searchDepth, int maxResults){}
    record TavilySearchResponse(List<Result> results){
        record Result(String title, String url, String content, Double score){}
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private RestClient.Builder restClientBuilder;

        private int maxResults = DEFAULT_MAX_RESULTS;

        private Builder() {}

        public Builder restClientBuilder(RestClient.Builder restClientBuilder) {
            this.restClientBuilder = restClientBuilder;
            return this;
        }

        public Builder maxResults(int maxResults) {
            if (maxResults <= 0) {
                throw new IllegalArgumentException("maxResults must be greater than 0");
            }
            this.maxResults = maxResults;
            return this;
        }

        public SearchEngineDocumentRetriever build() {
            return new SearchEngineDocumentRetriever(restClientBuilder, maxResults);
        }
    }

}
