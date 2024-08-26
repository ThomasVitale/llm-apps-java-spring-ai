package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestImageModelsOpenAiApplication {

    public static void main(String[] args) {
        SpringApplication.from(ImageModelsOpenAiApplication::main).with(TestImageModelsOpenAiApplication.class).run(args);
    }

}
