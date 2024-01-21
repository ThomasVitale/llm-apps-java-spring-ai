package com.thomasvitale.ai.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestDocumentReadersJsonOllamaApplication {

    @Bean
    @RestartScope
    @Scope("singleton") // needed because of https://github.com/spring-projects/spring-boot/issues/35786
    GenericContainer<?> ollama(DynamicPropertyRegistry properties) {
        var ollama = new GenericContainer<>("ghcr.io/thomasvitale/ollama-llama2")
                .withExposedPorts(11434);
        properties.add("spring.ai.ollama.base-url",
                () -> "http://%s:%s".formatted(ollama.getHost(), ollama.getMappedPort(11434)));
        return ollama;
    }

    public static void main(String[] args) {
        SpringApplication.from(DocumentReadersJsonOllamaApplication::main).with(TestDocumentReadersJsonOllamaApplication.class).run(args);
    }

}
