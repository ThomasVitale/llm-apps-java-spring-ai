package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPromptsMessagesOpenAiApplication {

    public static void main(String[] args) {
        SpringApplication.from(PromptsMessagesOpenAiApplication::main).with(TestPromptsMessagesOpenAiApplication.class).run(args);
    }

}
