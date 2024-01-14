package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPromptsMessagesApplication {

    public static void main(String[] args) {
        SpringApplication.from(PromptsMessagesApplication::main).with(TestPromptsMessagesApplication.class).run(args);
    }

}
