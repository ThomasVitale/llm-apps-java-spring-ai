package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestRagBasic {

    public static void main(String[] args) {
        SpringApplication.from(RagBasic::main).with(TestcontainersConfiguration.class).run(args);
    }

}
