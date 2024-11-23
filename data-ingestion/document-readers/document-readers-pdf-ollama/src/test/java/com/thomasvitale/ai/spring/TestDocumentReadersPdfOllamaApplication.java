package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestDocumentReadersPdfOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(DocumentReadersPdfOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
