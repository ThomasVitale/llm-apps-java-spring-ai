package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestMcpBraveApplication {

    public static void main(String[] args) {
        SpringApplication.from(McpBraveApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
