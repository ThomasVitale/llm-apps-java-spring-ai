package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "60s")
@Import(TestChatModelsOllamaApplication.class)
class ChatModelsOllamaApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void chat() {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/chat")
                        .queryParam("message", "What is the capital of Italy?")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                   assertThat(result).containsIgnoringCase("Rome");
                });
    }

}
