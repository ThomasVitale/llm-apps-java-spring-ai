package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestSemanticSearch {

    public static void main(String[] args) {
        SpringApplication.from(SemanticSearch::main).with(TestcontainersConfiguration.class).run(args);
    }

}
