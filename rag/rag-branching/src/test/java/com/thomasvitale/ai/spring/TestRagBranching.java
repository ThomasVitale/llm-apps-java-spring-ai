package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestRagBranching {

    public static void main(String[] args) {
        SpringApplication.from(RagBranching::main).with(TestcontainersConfiguration.class).run(args);
    }

}
