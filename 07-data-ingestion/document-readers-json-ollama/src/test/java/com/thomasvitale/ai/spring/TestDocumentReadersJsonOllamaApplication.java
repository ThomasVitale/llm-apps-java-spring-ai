package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestDocumentReadersJsonOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(DocumentReadersJsonOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
