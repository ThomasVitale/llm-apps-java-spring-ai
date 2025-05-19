package com.thomasvitale.ai.spring;

import com.thomasvitale.ai.spring.guardrails.SensitiveDataInputGuardrailAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SensitiveDataInputGuardrail {

    private final ChatClient chatClient;

    SensitiveDataInputGuardrail(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.clone()
                .defaultAdvisors(SensitiveDataInputGuardrailAdvisor.builder()
                        .chatClientBuilder(chatClientBuilder.clone())
                        .build())
                .build();
    }

    @PostMapping("/guardrails/input")
    String chat(@RequestBody String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

}
