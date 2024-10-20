package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestDocumentReadersTikaOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(DocumentReadersTikaOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
