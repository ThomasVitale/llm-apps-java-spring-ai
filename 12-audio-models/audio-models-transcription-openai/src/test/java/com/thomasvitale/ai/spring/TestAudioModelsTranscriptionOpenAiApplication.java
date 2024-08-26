package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAudioModelsTranscriptionOpenAiApplication {

    public static void main(String[] args) {
        SpringApplication.from(AudioModelsTranscriptionOpenAiApplication::main).with(TestAudioModelsTranscriptionOpenAiApplication.class).run(args);
    }

}
