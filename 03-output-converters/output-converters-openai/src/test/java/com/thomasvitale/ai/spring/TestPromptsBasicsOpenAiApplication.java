package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPromptsBasicsOpenAiApplication {

    public static void main(String[] args) {
        SpringApplication.from(OutputParsersBeanOpenAiApplication::main).with(TestPromptsBasicsOpenAiApplication.class).run(args);
    }

}
