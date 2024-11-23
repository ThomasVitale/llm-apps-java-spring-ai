package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Chatbot {

    public static void main(String[] args) {
        SpringApplication.run(Chatbot.class, args);
    }

    @Bean
    ChatMemory chatHistory() {
        return new InMemoryChatMemory();
    }

}
