package com.thomasvitale.ai.spring.advisor;

import com.thomasvitale.ai.spring.rag.Query;
import com.thomasvitale.ai.spring.rag.augmentation.ContentPromptAugmentor;
import com.thomasvitale.ai.spring.rag.augmentation.PromptAugmentor;
import com.thomasvitale.ai.spring.rag.orchestration.routing.AllRetrieversQueryRouter;
import com.thomasvitale.ai.spring.rag.orchestration.routing.QueryRouter;
import com.thomasvitale.ai.spring.rag.preretrieval.query.expansion.IdentityQueryExpander;
import com.thomasvitale.ai.spring.rag.preretrieval.query.expansion.QueryExpander;
import com.thomasvitale.ai.spring.rag.preretrieval.query.transformation.IdentityQueryTransformer;
import com.thomasvitale.ai.spring.rag.preretrieval.query.transformation.QueryTransformer;
import com.thomasvitale.ai.spring.rag.retrieval.combination.DocumentCombiner;
import com.thomasvitale.ai.spring.rag.retrieval.combination.MergeDocumentCombiner;
import com.thomasvitale.ai.spring.rag.retrieval.source.DocumentRetriever;
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
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
 *     .documentRetriever(documentRetriever)
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

    public static final String DOCUMENT_CONTEXT = "rag_document_context";

    private final List<QueryTransformer> queryTransformers;

    private final QueryExpander queryExpander;

    private final QueryRouter queryRouter;

    private final DocumentCombiner documentCombiner;

    private final PromptAugmentor promptAugmentor;

    private final Boolean protectFromBlocking;

    private final int order;

    public RetrievalAugmentationAdvisor(List<QueryTransformer> queryTransformers, @Nullable QueryExpander queryExpander, QueryRouter queryRouter, @Nullable DocumentCombiner documentCombiner, @Nullable PromptAugmentor promptAugmentor, @Nullable Boolean protectFromBlocking, @Nullable Integer order) {
        this.queryTransformers = queryTransformers.isEmpty() ? List.of(new IdentityQueryTransformer()) : queryTransformers;
        this.queryExpander = queryExpander != null ? queryExpander : new IdentityQueryExpander();
        this.queryRouter = queryRouter;
        this.documentCombiner = documentCombiner != null ? documentCombiner : new MergeDocumentCombiner();
        this.promptAugmentor = promptAugmentor != null ? promptAugmentor : ContentPromptAugmentor.builder().build();
        this.protectFromBlocking = protectFromBlocking != null ? protectFromBlocking : false;
        this.order = order != null ? order : 0;
    }

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        Assert.notNull(advisedRequest, "advisedRequest cannot be null");
        Assert.notNull(chain, "chain cannot be null");

        AdvisedRequest processedAdvisedRequest = before(advisedRequest);
        AdvisedResponse advisedResponse = chain.nextAroundCall(processedAdvisedRequest);
        return after(advisedResponse);
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        Assert.notNull(advisedRequest, "advisedRequest cannot be null");
        Assert.notNull(chain, "chain cannot be null");

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
        Map<String, Object> context = new HashMap<>(request.adviseContext());

        // 0. Create a query from the user text and parameters.
        Query originalQuery = new Query(new PromptTemplate(request.userText(), request.userParams()).render());

        // 1. Transform original user query based on a chain of query transformers.
        Query transformedQuery = originalQuery;
        for (var queryTransformer: queryTransformers) {
            transformedQuery = queryTransformer.apply(transformedQuery);
        }

        // 2. Expand query into one or multiple queries.
        List<Query> expandedQueries = queryExpander.expand(transformedQuery);

        // 3. Get document retrievers for each query.
        Map<Query,List<DocumentRetriever>> documentRetrieversForQuery = expandedQueries.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        queryRouter::route
                ));

        // 4. Retrieve similar documents for each query.
        Map<Query,List<List<Document>>> documentsForQuery = documentRetrieversForQuery.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(retriever -> retriever.retrieve(entry.getKey().text()))
                                .toList()
                ));

        // 5. Combine documents retrieved across multiple queries and retrievers.
        List<Document> documents = documentCombiner.combine(documentsForQuery);
        context.put(DOCUMENT_CONTEXT, documents);

        // 6. Augment user prompt with the document contextual data.
        UserMessage augmentedUserMessage = promptAugmentor.augment(originalQuery, documents);

        // 7. Update advised request with augmented prompt.
        return AdvisedRequest.from(request)
                .withUserText(augmentedUserMessage.getContent())
                .withAdviseContext(context)
                .build();
    }

    private AdvisedResponse after(AdvisedResponse advisedResponse) {
        ChatResponse.Builder chatResponseBuilder = ChatResponse.builder().from(advisedResponse.response());
        chatResponseBuilder.withMetadata(DOCUMENT_CONTEXT, advisedResponse.adviseContext().get(DOCUMENT_CONTEXT));
        return new AdvisedResponse(chatResponseBuilder.build(), advisedResponse.adviseContext());
    }

    private Predicate<AdvisedResponse> onFinishReason() {
        return advisedResponse -> advisedResponse.response()
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
        private List<QueryTransformer> queryTransformers = new ArrayList<>();
        private QueryExpander queryExpander;
        private QueryRouter queryRouter;
        private DocumentCombiner documentCombiner;
        private PromptAugmentor promptAugmentor;
        private Boolean protectFromBlocking;
        private Integer order;

        private Builder() {}

        public Builder queryTransformers(List<QueryTransformer> queryTransformers) {
            this.queryTransformers = queryTransformers;
            return this;
        }

        public Builder queryTransformer(QueryTransformer queryTransformer) {
            this.queryTransformers.add(queryTransformer);
            return this;
        }

        public Builder queryExpander(QueryExpander queryExpander) {
            this.queryExpander = queryExpander;
            return this;
        }

        public Builder queryRouter(QueryRouter queryRouter) {
            Assert.isNull(this.queryRouter, "Cannot set both documentRetriever and queryRouter");
            this.queryRouter = queryRouter;
            return this;
        }

        public Builder documentRetriever(DocumentRetriever documentRetriever) {
            Assert.isNull(this.queryRouter, "Cannot set both documentRetriever and queryRouter");
            this.queryRouter = AllRetrieversQueryRouter.builder()
                    .documentRetrievers(documentRetriever)
                    .build();
            return this;
        }

        public Builder documentCombiner(DocumentCombiner documentCombiner) {
            this.documentCombiner = documentCombiner;
            return this;
        }

        public Builder promptAugmentor(PromptAugmentor promptAugmentor) {
            this.promptAugmentor = promptAugmentor;
            return this;
        }

        public Builder protectFromBlocking(Boolean protectFromBlocking) {
            this.protectFromBlocking = protectFromBlocking;
            return this;
        }

        public Builder order(Integer order) {
            this.order = order;
            return this;
        }

        public RetrievalAugmentationAdvisor build() {
            return new RetrievalAugmentationAdvisor(queryTransformers, queryExpander, queryRouter, documentCombiner, promptAugmentor, protectFromBlocking, order);
        }
    }

}
