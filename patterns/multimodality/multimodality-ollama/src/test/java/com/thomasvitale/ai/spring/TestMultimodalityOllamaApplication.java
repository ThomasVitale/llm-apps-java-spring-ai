package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestMultimodalityOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(MultimodalityOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
