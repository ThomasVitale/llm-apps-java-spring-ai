package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestDocumentTransformersSplittersOllamaApplication {

    public static void main(String[] args) {
        SpringApplication.from(DocumentTransformersSplittersOllamaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
