package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestStructuredDataExtraction {

    public static void main(String[] args) {
        SpringApplication.from(StructuredDataExtraction::main).with(TestcontainersConfiguration.class).run(args);
    }

}
