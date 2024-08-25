package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestPromptsTemplatesOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(PromptsTemplatesOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
