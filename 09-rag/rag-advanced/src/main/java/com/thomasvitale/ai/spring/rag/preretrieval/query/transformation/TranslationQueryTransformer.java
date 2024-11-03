package com.thomasvitale.ai.spring.rag.preretrieval.query.transformation;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.thomasvitale.ai.spring.util.PromptAssert;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.rag.Query;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * A transformer that translates the user query to a target language.
 *
 * <p>Example usage:
 * <pre>{@code
 * QueryTransformer transformer = TranslationQueryTransformer.builder()
 *    .chatClientBuilder(chatClientBuilder)
 *    .promptTemplate(promptTemplate)
 *    .targetLanguage("english")
 *    .build();
 * Query transformedQuery = transformer.transform(new Query("Hvad er Danmarks hovedstad?"));
 * }</pre>
 */
public class TranslationQueryTransformer implements QueryTransformer {

    private static final PromptTemplate DEFAULT_PROMPT_TEMPLATE = new PromptTemplate("""
            Translate the user query to the target language: {targetLanguage}.
            If the user query is already written in the target language,
            return it as is without any modification.
            Original query: {query}
            """);

    private final ChatClient chatClient;
    private final PromptTemplate promptTemplate;
    private final String targetLanguage;

    public TranslationQueryTransformer(ChatClient.Builder chatClientBuilder, @Nullable PromptTemplate promptTemplate, String targetLanguage) {
        Assert.notNull(chatClientBuilder, "chatClientBuilder cannot be null");
        Assert.hasText(targetLanguage, "targetLanguage cannot be null or empty");

        this.chatClient = chatClientBuilder
                .defaultOptions(ChatOptionsBuilder.builder()
                        .withTemperature(0.0)
                        .build())
                .build();
        this.promptTemplate = promptTemplate != null ? promptTemplate : DEFAULT_PROMPT_TEMPLATE;
        this.targetLanguage = targetLanguage;

        PromptAssert.templateHasRequiredPlaceholders(this.promptTemplate, "targetLanguage", "query");
    }

    @Override
    public Query transform(Query query) {
        Assert.notNull(query, "query cannot be null");

        var translatedQuery = chatClient.prompt()
                .user(user -> user.text(promptTemplate.getTemplate())
                        .param("targetLanguage", targetLanguage)
                        .param("query", query.text())
                )
                .call()
                .entity(TranslatedQuery.class);
        return new Query(translatedQuery.text());
    }

    @JsonClassDescription("A translated version of the original query.")
    record TranslatedQuery(String text) {}

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ChatClient.Builder chatClientBuilder;
        private PromptTemplate promptTemplate;
        private String targetLanguage;

        private Builder() {}

        public Builder chatClientBuilder(ChatClient.Builder chatClientBuilder) {
            this.chatClientBuilder = chatClientBuilder;
            return this;
        }

        public Builder promptTemplate(PromptTemplate promptTemplate) {
            this.promptTemplate = promptTemplate;
            return this;
        }

        public Builder targetLanguage(String targetLanguage) {
            this.targetLanguage = targetLanguage;
            return this;
        }

        public TranslationQueryTransformer build() {
            return new TranslationQueryTransformer(chatClientBuilder, promptTemplate, targetLanguage);
        }
    }
}
