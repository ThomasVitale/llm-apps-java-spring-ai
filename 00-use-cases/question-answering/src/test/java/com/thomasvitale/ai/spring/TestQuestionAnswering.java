package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestQuestionAnswering {

    public static void main(String[] args) {
        SpringApplication.from(QuestionAnswering::main).with(TestcontainersConfiguration.class).run(args);
    }

}
