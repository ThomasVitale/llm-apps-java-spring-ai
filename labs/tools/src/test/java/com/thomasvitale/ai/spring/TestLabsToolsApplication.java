package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestLabsToolsApplication {

    public static void main(String[] args) {
        SpringApplication.from(LabsToolsApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
