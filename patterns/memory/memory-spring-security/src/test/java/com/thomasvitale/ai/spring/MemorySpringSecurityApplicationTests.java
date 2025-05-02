package com.thomasvitale.ai.spring;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "60s")
class MemorySpringSecurityApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/memory/security"})
    void chat(String path) {
        var webTestClient1 = webTestClient.mutate()
                .filter(basicAuthentication("george", "lizard"))
                .build();

        webTestClient1
                .post()
                .uri(path)
                .bodyValue("My name is Bond. James Bond.")
                .exchange()
                .expectStatus().isOk();

        webTestClient1
                .post()
                .uri(path)
                .bodyValue("What's my name?")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("Bond");
                });

        var webTestClient2 = webTestClient.mutate()
                .filter(basicAuthentication("isabella", "butterfly"))
                .build();

        webTestClient2
                .post()
                .uri(path)
                .bodyValue("What's my name?")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).doesNotContainIgnoringCase("Bond");
                });

    }

}
