package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestRagAdvanced {

    public static void main(String[] args) {
        SpringApplication.from(RagAdvanced::main).with(TestcontainersConfiguration.class).run(args);
    }

}
