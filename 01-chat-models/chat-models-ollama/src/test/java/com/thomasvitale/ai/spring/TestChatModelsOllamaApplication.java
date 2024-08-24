package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestChatModelsOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(ChatModelsOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
