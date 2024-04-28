package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestChatModelsMistralAiApplication {

    public static void main(String[] args) {
        SpringApplication.from(ChatModelsMistralAiApplication::main).with(TestChatModelsMistralAiApplication.class).run(args);
    }

}
