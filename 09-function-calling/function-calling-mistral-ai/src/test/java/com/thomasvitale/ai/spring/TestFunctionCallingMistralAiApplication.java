package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestFunctionCallingMistralAiApplication {

    public static void main(String[] args) {
        SpringApplication.from(FunctionCallingMistralAiApplication::main).with(TestFunctionCallingMistralAiApplication.class).run(args);
    }

}
