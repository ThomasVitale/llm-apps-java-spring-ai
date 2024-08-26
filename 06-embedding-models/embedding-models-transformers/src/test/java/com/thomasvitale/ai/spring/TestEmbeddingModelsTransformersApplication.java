package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestEmbeddingModelsTransformersApplication {

    public static void main(String[] args) {
        SpringApplication.from(EmbeddingModelsTransformersApplication::main).with(TestEmbeddingModelsTransformersApplication.class).run(args);
    }

}
