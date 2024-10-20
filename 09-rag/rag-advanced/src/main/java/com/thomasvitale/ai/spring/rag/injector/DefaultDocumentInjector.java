package com.thomasvitale.ai.spring.rag.injector;

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
 * Default implementation of the {@link DocumentInjector} interface.
 * This component is responsible for injecting documents into user messages
 * as part of a Retrieval-Augmented Generation (RAG) workflow.
 */
public class DefaultDocumentInjector implements DocumentInjector {

    private static final PromptTemplate DEFAULT_PROMPT_TEMPLATE = new PromptTemplate("""
            {user_message}

            Context information is below. Use this information to answer the user's question.

            ---------------------
            {context}
            ---------------------
   
            Given the context and provided history information and not prior knowledge,
            reply to the user message. If the answer is not in the context, inform
            the user that you can't answer the question.
            """);

    private final PromptTemplate promptTemplate;

    public DefaultDocumentInjector(@Nullable PromptTemplate promptTemplate) {
        this.promptTemplate = promptTemplate != null ? promptTemplate : DEFAULT_PROMPT_TEMPLATE;
    }

    @Override
    public UserMessage inject(UserMessage userMessage, List<Document> documents) {
        Assert.notNull(userMessage, "userMessage cannot be null");
        Assert.notNull(documents, "documents cannot be null");

        // 1. Combine documents.
        String documentContext = documents.stream()
                .map(Content::getContent)
                .collect(Collectors.joining(System.lineSeparator()));

        // 2. Define prompt parameters.
        Map<String, Object> promptParameters = Map.of(
                "user_message", userMessage,
                "context", documentContext);

        // 3. Inject documents into user message.
        return (UserMessage) promptTemplate.createMessage(promptParameters);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PromptTemplate promptTemplate;

        public Builder withPromptTemplate(PromptTemplate promptTemplate) {
            this.promptTemplate = promptTemplate;
            return this;
        }

        public DefaultDocumentInjector build() {
            return new DefaultDocumentInjector(promptTemplate);
        }
    }

}
