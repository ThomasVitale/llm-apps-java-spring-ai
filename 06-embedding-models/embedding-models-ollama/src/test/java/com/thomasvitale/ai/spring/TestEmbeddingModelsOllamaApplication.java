package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestEmbeddingModelsOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(EmbeddingModelsOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
