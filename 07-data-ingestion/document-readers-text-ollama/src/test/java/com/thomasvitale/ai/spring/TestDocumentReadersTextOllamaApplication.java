package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestDocumentReadersTextOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(DocumentReadersTextOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
