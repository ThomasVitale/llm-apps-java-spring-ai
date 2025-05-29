package com.thomasvitale.ai.spring.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.postretrieval.document.DocumentPostProcessor;
import org.springframework.ai.rag.util.PromptAssert;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Uses a large language model to compress a document content to reduce noise and redundancy.
 * This is useful when the document content is too long and contains a lot of unnecessary information.
 */
public class CompressionDocumentPostProcessor implements DocumentPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(CompressionDocumentPostProcessor.class);

    private static final PromptTemplate DEFAULT_PROMPT_TEMPLATE = new PromptTemplate("""
			Given the following contextual information and input query, your task is to synthesize
			a compressed version of the context that is relevant to answer the input query,
			reducing noise and redundancy.

			Contextual information:
			{context}

			User query:
			{query}

			Compressed contextual information:
			""");

    private final ChatClient chatClient;

    private final PromptTemplate promptTemplate;

    private CompressionDocumentPostProcessor(ChatClient.Builder chatClientBuilder, @Nullable PromptTemplate promptTemplate) {
        Assert.notNull(chatClientBuilder, "chatClientBuilder cannot be null");

        this.chatClient = chatClientBuilder.build();
        this.promptTemplate = promptTemplate != null ? promptTemplate : DEFAULT_PROMPT_TEMPLATE;

        PromptAssert.templateHasRequiredPlaceholders(this.promptTemplate, "context", "query");
    }

    @Override
    public List<Document> process(Query query, List<Document> documents) {
        Assert.notNull(query, "query cannot be null");
        Assert.notNull(documents, "documents cannot be null");
        Assert.noNullElements(documents, "documents cannot contain null elements");

        if (CollectionUtils.isEmpty(documents)) {
            return documents;
        }

        logger.debug("Compressing documents for query: {}", query.text());

        return documents.stream()
                .map(document -> document.mutate()
                        .text(this.chatClient.prompt()
                                .user(user -> user.text(this.promptTemplate.getTemplate())
                                        .param("context", document.getText() != null ? document.getText() : "")
                                        .param("query", query.text()))
                                .options(ChatOptions.builder()
                                        .temperature(0.2)
                                        .build())
                                .call()
                                .content())
                        .build())
                .toList();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ChatClient.Builder chatClientBuilder;
        private PromptTemplate promptTemplate = DEFAULT_PROMPT_TEMPLATE;

        private Builder() {}

        public Builder chatClientBuilder(ChatClient.Builder chatClientBuilder) {
            this.chatClientBuilder = chatClientBuilder;
            return this;
        }

        public Builder promptTemplate(PromptTemplate promptTemplate) {
            this.promptTemplate = promptTemplate;
            return this;
        }

        public CompressionDocumentPostProcessor build() {
            return new CompressionDocumentPostProcessor(this.chatClientBuilder, this.promptTemplate);
        }
    }

}
