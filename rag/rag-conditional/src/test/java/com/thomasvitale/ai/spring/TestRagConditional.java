package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestRagConditional {

    public static void main(String[] args) {
        SpringApplication.from(RagConditional::main).with(TestcontainersConfiguration.class).run(args);
    }

}
