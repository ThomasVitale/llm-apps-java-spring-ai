package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestDocumentReadersMarkdownOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(DocumentReadersMarkdownOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
