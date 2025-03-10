package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RagAdvanced {

    public static void main(String[] args) {
        SpringApplication.run(RagAdvanced.class, args);
    }

    @Bean
    ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

}
