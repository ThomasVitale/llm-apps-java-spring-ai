package com.thomasvitale.ai.spring;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "60s")
class GuardrailsOutputApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/guardrails/output"})
    void chat(String path) {
        webTestClient
                .post()
                .uri(path)
                .bodyValue(new MusicQuestion("rock", "piano"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArtistInfo.class).value(result -> {
                    assertThat(result).isNotNull();
                    assertThat(result.name()).isNotEmpty();
                    assertThat(result.band()).isNotEmpty();
                });
    }

}
