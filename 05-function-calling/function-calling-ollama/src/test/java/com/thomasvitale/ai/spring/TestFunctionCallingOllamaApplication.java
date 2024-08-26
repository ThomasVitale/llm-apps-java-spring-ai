package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestFunctionCallingOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(FunctionCallingOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
