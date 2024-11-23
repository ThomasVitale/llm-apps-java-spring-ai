package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestDocumentTransformersMetadataOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(DocumentTransformersMetadataOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
