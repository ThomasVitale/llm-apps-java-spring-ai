package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestObservabilityVectorStoresPgVector {

    public static void main(String[] args) {
        SpringApplication.from(ObservabilityVectorStoresPgVector::main).with(TestcontainersConfiguration.class).run(args);
    }

}
