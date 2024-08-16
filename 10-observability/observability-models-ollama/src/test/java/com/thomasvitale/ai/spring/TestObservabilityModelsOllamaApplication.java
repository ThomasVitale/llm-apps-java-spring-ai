package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestObservabilityModelsOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(ObservabilityModelsOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
