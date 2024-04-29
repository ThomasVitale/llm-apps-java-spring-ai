package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestChatModelsOpenAApplication {

    public static void main(String[] args) {
        SpringApplication.from(ChatModelsOpenAiApplication::main).with(TestChatModelsOpenAApplication.class).run(args);
    }

}
