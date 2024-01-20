package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestChatModelsOllamaApplication.class)
@AutoConfigureWebTestClient(timeout = "30s")
class ChatModelsOllamaApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void chat() {
        webTestClient
                .get()
                .uri("/ai/chat")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(response -> {
                   assertThat(response).contains("You shall not pass");
                });
    }

}
