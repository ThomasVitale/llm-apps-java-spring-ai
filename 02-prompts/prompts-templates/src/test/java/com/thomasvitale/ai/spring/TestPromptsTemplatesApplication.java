package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPromptsTemplatesApplication {

    public static void main(String[] args) {
        SpringApplication.from(PromptsTemplatesApplication::main).with(TestPromptsTemplatesApplication.class).run(args);
    }

}
