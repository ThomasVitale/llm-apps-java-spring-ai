package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "120s")
@TestPropertySource(properties = "arconia.dev.services.ollama.enabled=true")
@Disabled
class ToolCallingOllamaApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/chat/function", "/model/chat/function", "/chat/function/explicit", "/model/chat/function/explicit"})
    void chat(String path) {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("authorName", "Philip Pullman")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("His Dark Materials");
                });
    }

}
