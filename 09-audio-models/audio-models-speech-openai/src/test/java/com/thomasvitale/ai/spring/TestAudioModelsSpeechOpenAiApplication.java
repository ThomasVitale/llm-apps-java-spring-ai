package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAudioModelsSpeechOpenAiApplication {

    public static void main(String[] args) {
        SpringApplication.from(AudioModelsSpeechOpenAiApplication::main).with(TestAudioModelsSpeechOpenAiApplication.class).run(args);
    }

}
