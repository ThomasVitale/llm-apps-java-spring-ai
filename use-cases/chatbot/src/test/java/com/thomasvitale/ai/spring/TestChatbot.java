package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;

public class TestChatbot {

    public static void main(String[] args) {
        SpringApplication.from(Chatbot::main).with(TestcontainersConfiguration.class).run(args);
    }

}
