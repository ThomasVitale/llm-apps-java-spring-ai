package com.thomasvitale.ai.spring;

import com.thomasvitale.ai.spring.components.SearchEngineDocumentRetriever;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class RagControllerQueryRouting {

    private final ChatClient chatClient;

    public RagControllerQueryRouting(ChatClient.Builder chatClientBuilder, RestClient.Builder restClientBuilder, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder
                .defaultTools(new Tools(chatClientBuilder.clone(), restClientBuilder, vectorStore))
                .build();
    }

    @PostMapping("/rag/query/routing")
    String rag(@RequestBody String input) {
        return chatClient.prompt()
                .user(input)
                .call()
                .content();
    }

    static class Tools {

        private final ChatClient.Builder chatClientBuilder;
        private final RestClient.Builder restClientBuilder;
        private final VectorStore vectorStore;

        Tools(ChatClient.Builder chatClientBuilder, RestClient.Builder restClientBuilder, VectorStore vectorStore) {
            this.chatClientBuilder = chatClientBuilder;
            this.restClientBuilder = restClientBuilder;
            this.vectorStore = vectorStore;
        }

        @Tool(description = "Retrieve information about stories taking place in the world of Iorek and Pingu", returnDirect = true)
        String iorekPinguRetriever(String query) {
            return chatClientBuilder.clone().build().prompt()
                    .advisors(RetrievalAugmentationAdvisor.builder()
                            .documentRetriever(VectorStoreDocumentRetriever.builder()
                                    .filterExpression(new FilterExpressionBuilder().eq("location", "North Pole").build())
                                    .vectorStore(vectorStore)
                                    .similarityThreshold(0.5)
                                    .topK(3)
                                    .build())
                            .build())
                    .user(query)
                    .call()
                    .content();
        }

        @Tool(description = "Retrieve information about stories taking place in the world of Lucio and Balosso", returnDirect = true)
        String lucioBalossoRetriever(String query) {
            return chatClientBuilder.clone().build().prompt()
                    .advisors(RetrievalAugmentationAdvisor.builder()
                            .documentRetriever(VectorStoreDocumentRetriever.builder()
                                    .filterExpression(new FilterExpressionBuilder().eq("location", "Italy").build())
                                    .vectorStore(vectorStore)
                                    .similarityThreshold(0.5)
                                    .topK(3)
                                    .build())
                            .build())
                    .user(query)
                    .call()
                    .content();
        }

        @Tool(description = "Retrieve information by searching the web", returnDirect = true)
        String webSearchRetriever(String query) {
            return chatClientBuilder.clone().build().prompt()
                    .advisors(RetrievalAugmentationAdvisor.builder()
                            .documentRetriever(SearchEngineDocumentRetriever.builder()
                                    .restClientBuilder(restClientBuilder)
                                    .maxResults(10)
                                    .build())
                            .build())
                    .user(query)
                    .call()
                    .content();
        }

    }

}
