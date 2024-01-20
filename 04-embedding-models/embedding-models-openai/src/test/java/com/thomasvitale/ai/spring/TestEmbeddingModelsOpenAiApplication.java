package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestEmbeddingModelsOpenAiApplication {

    public static void main(String[] args) {
        SpringApplication.from(EmbeddingModelsOpenAiApplication::main).with(TestEmbeddingModelsOpenAiApplication.class).run(args);
    }

}
