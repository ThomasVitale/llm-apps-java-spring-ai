package com.thomasvitale.ai.spring.rag.preretrieval.query.expansion;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.thomasvitale.ai.spring.rag.Query;
import com.thomasvitale.ai.spring.util.PromptAssert;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.List;

/**
 * An expander that generates multiple versions of the user's original question.
 *
 * <p>Example usage:
 * <pre>{@code
 * MultiQueryExpander expander = MultiQueryExpander.builder()
 *    .withChatClientBuilder(chatClientBuilder)
 *    .withPromptTemplate(promptTemplate)
 *    .withIncludeOriginal(true)
 *    .withNumberOfQueries(3)
 *    .build();
 * List<Query> queries = expander.expand(new Query("What's the meaning of life?"));
 * }</pre>
 */
public class MultiQueryExpander implements QueryExpander {

    // Based on the prompt from LangChain's MultiQueryRetriever.
    // https://python.langchain.com/api_reference/_modules/langchain/retrievers/multi_query.html
    private static final PromptTemplate DEFAULT_PROMPT_TEMPLATE = new PromptTemplate("""
            You are an AI language model assistant. Your task is to generate
            {number} different versions of the given user query to retrieve
            relevant documents from a vector database.
            
            By generating multiple perspectives on the user query,
            your goal is to help the user overcome some of the limitations
            of distance-based similarity search.
            
            Original query: {query}
            """);

    private static final Boolean DEFAULT_INCLUDE_ORIGINAL = false;

    private static final Integer DEFAULT_NUMBER_OF_QUERIES = 3;

    private final ChatClient chatClient;
    private final PromptTemplate promptTemplate;
    private final boolean includeOriginal;
    private final int numberOfQueries;

    public MultiQueryExpander(ChatClient.Builder chatClientBuilder, @Nullable PromptTemplate promptTemplate, @Nullable Boolean includeOriginal, @Nullable Integer numberOfQueries) {
        Assert.notNull(chatClientBuilder, "chatClient cannot be null");

        this.chatClient = chatClientBuilder.build();
        this.promptTemplate = promptTemplate != null ? promptTemplate : DEFAULT_PROMPT_TEMPLATE;
        this.includeOriginal = includeOriginal != null ? includeOriginal : DEFAULT_INCLUDE_ORIGINAL;
        this.numberOfQueries = numberOfQueries != null ? numberOfQueries : DEFAULT_NUMBER_OF_QUERIES;

        PromptAssert.templateHasRequiredPlaceholders(this.promptTemplate, "number", "query");
    }

    @Override
    public List<Query> expand(Query query) {
        Assert.notNull(query, "query cannot be null");

        var queryVariants = chatClient.prompt()
                .user(user -> user.text(promptTemplate.getTemplate())
                        .param("number", numberOfQueries)
                        .param("query", query.text())
                )
                .call()
                .entity(new ParameterizedTypeReference<List<QueryVariant>>() {});

        var queries = queryVariants.stream()
                .map(queryVariant -> new Query(queryVariant.text()))
                .toList();

        if (includeOriginal) {
            queries.addFirst(query);
        }

        return queries;
    }

    @JsonClassDescription("A variant of the user query.")
    record QueryVariant(String text) {}

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ChatClient.Builder chatClientBuilder;
        private PromptTemplate promptTemplate;
        private Boolean includeOriginal;
        private Integer numberOfQueries;

        private Builder() {}

        public Builder chatClientBuilder(ChatClient.Builder chatClientBuilder) {
            this.chatClientBuilder = chatClientBuilder;
            return this;
        }

        public Builder promptTemplate(PromptTemplate promptTemplate) {
            this.promptTemplate = promptTemplate;
            return this;
        }

        public Builder includeOriginal(Boolean includeOriginal) {
            this.includeOriginal = includeOriginal;
            return this;
        }

        public Builder numberOfQueries(Integer numberOfQueries) {
            this.numberOfQueries = numberOfQueries;
            return this;
        }

        public MultiQueryExpander build() {
            return new MultiQueryExpander(chatClientBuilder, promptTemplate, includeOriginal, numberOfQueries);
        }
    }

}
