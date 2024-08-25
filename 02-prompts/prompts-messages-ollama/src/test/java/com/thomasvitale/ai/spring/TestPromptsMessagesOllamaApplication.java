package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestPromptsMessagesOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(PromptsMessagesOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
