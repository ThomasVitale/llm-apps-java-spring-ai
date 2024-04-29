package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestFunctionCallingOpenAiApplication {

    public static void main(String[] args) {
        SpringApplication.from(FunctionCallingOpenAiApplication::main).with(TestFunctionCallingOpenAiApplication.class).run(args);
    }

}
