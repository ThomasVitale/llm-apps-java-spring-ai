package com.thomasvitale.ai.spring.rag;

import com.thomasvitale.ai.spring.rag.injector.DefaultDocumentInjector;
import com.thomasvitale.ai.spring.rag.injector.DocumentInjector;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisorChain;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentRetriever;
import org.springframework.ai.model.Content;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * An advisor that retrieves similar documents based on the user's query
 * and augments the query with the retrieved documents to provide context-aware responses.
 * This advisor implements a Retrieval-Augmented Generation (RAG) workflow.
 *
 * <p>Example usage:
 * <pre>{@code
 * DocumentRetriever documentRetriever = ...;
 * RetrievalAugmentationAdvisor ragAdvisor = RetrievalAugmentationAdvisor.builder()
 *     .withDocumentRetriever(documentRetriever)
 *     .build();
 * String response = chatClient.prompt(query)
 *     .advisors(ragAdvisor)
 *     .call()
 *     .content()
 * }</pre>
 *
 * The implementation is based on the built-in {@link QuestionAnswerAdvisor}.
 */
public class RetrievalAugmentationAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

    private static final int DEFAULT_ORDER = 0;

    private static final DocumentInjector DEFAULT_DOCUMENT_INJECTOR = DefaultDocumentInjector.builder().build();

    public static final String RETRIEVED_DOCUMENTS = "qa_retrieved_documents";

    private final DocumentRetriever documentRetriever;

    private final DocumentInjector documentInjector;

    private final Boolean protectFromBlocking;

    private final int order;

    public RetrievalAugmentationAdvisor(DocumentRetriever documentRetriever, @Nullable DocumentInjector documentInjector, @Nullable Boolean protectFromBlocking, @Nullable Integer order) {
        this.documentRetriever = documentRetriever;
        this.documentInjector = documentInjector != null ? documentInjector : DEFAULT_DOCUMENT_INJECTOR;
        this.protectFromBlocking = protectFromBlocking != null ? protectFromBlocking : false;
        this.order = order != null ? order : DEFAULT_ORDER;
    }

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        // transform query
        // list of queries
        // for each query, router, then retriever
        // map query -> List<Document>
        // aggregate documents
        // add context to original prompt

        AdvisedRequest processedAdvisedRequest = before(advisedRequest);

        AdvisedResponse advisedResponse = chain.nextAroundCall(processedAdvisedRequest);

        return after(advisedResponse);
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        Flux<AdvisedResponse> advisedResponses = (this.protectFromBlocking) ?
                Mono.just(advisedRequest)
                        .publishOn(Schedulers.boundedElastic())
                        .map(this::before)
                        .flatMapMany(chain::nextAroundStream)
                : chain.nextAroundStream(before(advisedRequest));

        return advisedResponses.map(ar -> {
            if (onFinishReason().test(ar)) {
                ar = after(ar);
            }
            return ar;
        });
    }

    private AdvisedRequest before(AdvisedRequest request) {
        var context = new HashMap<>(request.adviseContext());

        var userMessage = new PromptTemplate(request.userText(), request.userParams()).render();

        // 1. Retrieve similar documents.
        List<Document> documents = documentRetriever.retrieve(userMessage);
        context.put(RETRIEVED_DOCUMENTS, documents);

        // 2. Inject documents into user message.
        UserMessage augmentedUserMessage = documentInjector.inject(userMessage, documents);

        // 3. Build advised request with augmented prompt.
        return AdvisedRequest.from(request)
                .withUserText(augmentedUserMessage.getContent())
                .withAdviseContext(context)
                .build();
    }

    private AdvisedResponse after(AdvisedResponse advisedResponse) {
        ChatResponse.Builder chatResponseBuilder = ChatResponse.builder().from(advisedResponse.response());
        chatResponseBuilder.withMetadata(RETRIEVED_DOCUMENTS, advisedResponse.adviseContext().get(RETRIEVED_DOCUMENTS));
        return new AdvisedResponse(chatResponseBuilder.build(), advisedResponse.adviseContext());
    }

    private Predicate<AdvisedResponse> onFinishReason() {
        return (advisedResponse) -> advisedResponse.response()
                .getResults()
                .stream()
                .anyMatch(result -> result != null && result.getMetadata() != null
                        && StringUtils.hasText(result.getMetadata().getFinishReason()));
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return order;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private DocumentRetriever documentRetriever;
        private DocumentInjector documentInjector;
        private Boolean protectFromBlocking;
        private Integer order;

        private Builder() {}

        public Builder withDocumentRetriever(DocumentRetriever documentRetriever) {
            this.documentRetriever = documentRetriever;
            return this;
        }

        public Builder withDocumentInjector(DocumentInjector documentInjector) {
            this.documentInjector = documentInjector;
            return this;
        }

        public Builder withProtectFromBlocking(Boolean protectFromBlocking) {
            this.protectFromBlocking = protectFromBlocking;
            return this;
        }

        public Builder withOrder(Integer order) {
            this.order = order;
            return this;
        }

        public RetrievalAugmentationAdvisor build() {
            return new RetrievalAugmentationAdvisor(documentRetriever, documentInjector, protectFromBlocking, order);
        }
    }

}
