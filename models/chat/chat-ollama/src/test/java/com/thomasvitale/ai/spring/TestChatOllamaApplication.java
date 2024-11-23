package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestChatOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(ChatOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
