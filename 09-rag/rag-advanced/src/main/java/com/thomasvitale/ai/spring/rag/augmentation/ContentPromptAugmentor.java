package com.thomasvitale.ai.spring.rag.augmentation;

import com.thomasvitale.ai.spring.rag.Query;
import com.thomasvitale.ai.spring.util.PromptAssert;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.model.Content;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Augments the user prompt with the full content of each document.
 */
public class ContentPromptAugmentor implements PromptAugmentor {

    private static final PromptTemplate DEFAULT_PROMPT_TEMPLATE = new PromptTemplate("""
            {query}

            Context information is below. Use this information to answer the user query.

            ---------------------
            {context}
            ---------------------
   
            Given the context and provided history information and not prior knowledge,
            reply to the user query. If the answer is not in the context, inform
            the user that you can't answer the query.
            """);

    private static final PromptTemplate DEFAULT_EMPTY_CONTEXT_PROMPT_TEMPLATE = new PromptTemplate("""
            The user asked the following question:
            
            {query}
            
            Politely inform the user that you can't answer the query
            because it's outside the knowledge base available to you.
            You are not allowed to answer any question outside that knowledge base context.
            """);

    private static final boolean DEFAULT_ALLOW_EMPTY_CONTEXT = true;

    private final PromptTemplate promptTemplate;
    private final PromptTemplate emptyContextPromptTemplate;
    private final boolean allowEmptyContext;

    public ContentPromptAugmentor(@Nullable PromptTemplate promptTemplate, @Nullable PromptTemplate emptyContextPromptTemplate, @Nullable Boolean allowEmptyContext) {
        this.promptTemplate = promptTemplate != null ? promptTemplate : DEFAULT_PROMPT_TEMPLATE;
        this.emptyContextPromptTemplate = emptyContextPromptTemplate != null ? emptyContextPromptTemplate : DEFAULT_EMPTY_CONTEXT_PROMPT_TEMPLATE;
        this.allowEmptyContext = allowEmptyContext != null ? allowEmptyContext : DEFAULT_ALLOW_EMPTY_CONTEXT;
        PromptAssert.templateHasRequiredPlaceholders(this.promptTemplate, "query", "context");
    }

    @Override
    public UserMessage augment(Query query, List<Document> documents) {
        Assert.notNull(query, "query cannot be null");
        Assert.notNull(documents, "documents cannot be null");

        if (documents.isEmpty()) {
            return buildMessageWhenEmptyContext(query);
        }

        // 1. Join documents.
        String documentContext = documents.stream()
                .map(Content::getContent)
                .collect(Collectors.joining(System.lineSeparator()));

        // 2. Define prompt parameters.
        Map<String, Object> promptParameters = Map.of(
                "query", query.text(),
                "context", documentContext);

        // 3. Augment user prompt with document context.
        return (UserMessage) promptTemplate.createMessage(promptParameters);
    }

    private UserMessage buildMessageWhenEmptyContext(Query query) {
        if (allowEmptyContext) {
            return new UserMessage(query.text());
        }

        // 1. Define prompt parameters.
        Map<String, Object> promptParameters = Map.of("query", query.text());

        // 2. Augment user prompt with empty context.
        return (UserMessage) emptyContextPromptTemplate.createMessage(promptParameters);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PromptTemplate promptTemplate;
        private PromptTemplate emptyContextPromptTemplate;
        private Boolean allowEmptyContext;

        public Builder promptTemplate(PromptTemplate promptTemplate) {
            this.promptTemplate = promptTemplate;
            return this;
        }

        public Builder emptyContextPromptTemplate(PromptTemplate emptyContextPromptTemplate) {
            this.emptyContextPromptTemplate = emptyContextPromptTemplate;
            return this;
        }

        public Builder allowEmptyContext(Boolean allowEmptyContext) {
            this.allowEmptyContext = allowEmptyContext;
            return this;
        }

        public ContentPromptAugmentor build() {
            return new ContentPromptAugmentor(promptTemplate, emptyContextPromptTemplate, allowEmptyContext);
        }
    }

}
