package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestPromptBasicsOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(PromptsBasicsOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
