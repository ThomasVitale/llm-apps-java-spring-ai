package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestChatModelsOpenaiApplication {

    public static void main(String[] args) {
        SpringApplication.from(ChatModelsOpenaiApplication::main).with(TestChatModelsOpenaiApplication.class).run(args);
    }

}
