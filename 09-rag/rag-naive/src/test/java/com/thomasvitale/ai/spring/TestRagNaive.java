package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestRagNaive {

    public static void main(String[] args) {
        SpringApplication.from(RagNaive::main).with(TestcontainersConfiguration.class).run(args);
    }

}
