package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestObservabilityModelsMistralAiApplication {

    public static void main(String[] args) {
        SpringApplication.from(ObservabilityModelsMistralAiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
