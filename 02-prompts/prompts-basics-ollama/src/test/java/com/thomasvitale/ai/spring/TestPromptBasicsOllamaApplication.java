package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPromptBasicsOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(PromptsBasicsOllamaApplication::main).with(TestPromptBasicsOllamaApplication.class).run(args);
    }

}
