package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

@RestController
class ChatbotController {

    private final ChatClient chatClient;

    ChatbotController(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }

    @PostMapping("/chat/{conversationId}")
    String chat(@PathVariable String conversationId, @RequestBody String question) {
        return chatClient
                .prompt()
                .user(question)
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId))
                .call()
                .content();
    }

}
