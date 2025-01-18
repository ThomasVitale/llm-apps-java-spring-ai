package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestToolCallingOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(ToolCallingOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
