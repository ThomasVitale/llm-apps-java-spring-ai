package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPromptMultimodalityOpenAiApplication {

    public static void main(String[] args) {
        SpringApplication.from(PromptMultimodalityOpenAiApplication::main).with(TestPromptMultimodalityOpenAiApplication.class).run(args);
    }

}
