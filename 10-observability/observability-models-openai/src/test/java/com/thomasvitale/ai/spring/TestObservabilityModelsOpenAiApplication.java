package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestObservabilityModelsOpenAiApplication {

    public static void main(String[] args) {
        SpringApplication.from(ObservabilityModelsOpenAiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
