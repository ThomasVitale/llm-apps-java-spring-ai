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
class MemoryJdbcApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/memory/jdbc/007"})
    void chat(String path) {
        webTestClient
                .post()
                .uri(path)
                .bodyValue("My name is Bond. James Bond.")
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .post()
                .uri(path)
                .bodyValue("What's my name?")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("Bond");
                });

        webTestClient
                .post()
                .uri(path)
                .bodyValue("I was counting on your discretion. Please, do not share my name!")
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .post()
                .uri(path)
                .bodyValue("What's my name?")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("Bond");
                });

        webTestClient
                .post()
                .uri(path)
                .bodyValue("I was counting on your discretion. Please, do not share my name!")
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .post()
                .uri(path)
                .bodyValue("What did I just tell you?")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("name");
                });
    }

}
