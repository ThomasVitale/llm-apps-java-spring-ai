package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestPromptsMultimodalityOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(PromptsMultimodalityOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
