package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestChatModelsOpenAiApplication {

    public static void main(String[] args) {
        SpringApplication.from(ChatModelsOpenAiApplication::main).with(TestChatModelsOpenAiApplication.class).run(args);
    }

}
