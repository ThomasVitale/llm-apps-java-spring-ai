package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPromptsTemplatesOpenAiApplication {

    public static void main(String[] args) {
        SpringApplication.from(PromptsTemplatesOpenAiApplication::main).with(TestPromptsTemplatesOpenAiApplication.class).run(args);
    }

}
