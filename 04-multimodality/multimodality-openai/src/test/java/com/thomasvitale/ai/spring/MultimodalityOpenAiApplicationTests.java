package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "60s")
@EnabledIfEnvironmentVariable(named = "SPRING_AI_OPENAI_API_KEY", matches = ".*")
class MultimodalityOpenAiApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void chatFromImageFile() {
        webTestClient
                .get()
                .uri("/chat/image/file")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                   assertThat(result).containsIgnoringCase("cat");
                });
    }

    @Test
    void chatFromImageUrl() {
        webTestClient
                .get()
                .uri("/chat/image/url")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("dice");
                });
    }

}
