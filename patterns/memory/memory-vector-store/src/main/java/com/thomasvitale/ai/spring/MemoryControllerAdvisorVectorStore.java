package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class MemoryControllerAdvisorVectorStore {

    private final ChatClient chatClient;

    MemoryControllerAdvisorVectorStore(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder.clone()
                .defaultAdvisors(VectorStoreChatMemoryAdvisor.builder(vectorStore).build())
                .build();
    }

    @PostMapping("/memory/vector/{conversationId}")
    String chat(@PathVariable String conversationId, @RequestBody String question) {
        return chatClient.prompt()
                .user(question)
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId))
                .call()
                .content();
    }

}
