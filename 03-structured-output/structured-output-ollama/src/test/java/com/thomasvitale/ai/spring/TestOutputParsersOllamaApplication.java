package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestOutputParsersOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(OutputParsersOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
