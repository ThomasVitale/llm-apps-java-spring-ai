package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPromptBasicsApplication {

    public static void main(String[] args) {
        SpringApplication.from(PromptsBasicsApplication::main).with(TestPromptBasicsApplication.class).run(args);
    }

}
