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
class GuardrailsInputApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/guardrails/input"})
    void chat(String path) {
        webTestClient
                .post()
                .uri(path)
                .bodyValue("What's the capital of Italy?")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("Rome");
                });

        webTestClient
                .post()
                .uri(path)
                .bodyValue("What's the capital of Italy?")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("Computer says no");
                });
    }

}
