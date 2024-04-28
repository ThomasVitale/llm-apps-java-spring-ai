package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestEmbeddingModelsMistralAiApplication {

    public static void main(String[] args) {
        SpringApplication.from(EmbeddingModelsMistralAiApplication::main).with(TestEmbeddingModelsMistralAiApplication.class).run(args);
    }

}
