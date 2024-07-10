package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestChatModelsMultipleProvidersApplication {

    public static void main(String[] args) {
        SpringApplication.from(ChatModelsMultipleProvidersApplication::main).with(TestChatModelsMultipleProvidersApplication.class).run(args);
    }

}
