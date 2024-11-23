package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestEmbeddingOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(EmbeddingOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
