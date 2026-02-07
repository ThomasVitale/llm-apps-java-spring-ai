package com.thomasvitale.ai.spring;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class MemoryBasicsApplicationTests {

    @Autowired
    RestTestClient restTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/memory/messages/007", "/memory/prompt/007"})
    void chat(String path) {
        restTestClient
                .post()
                .uri(path)
                .body("My name is Bond. James Bond.")
                .exchange()
                .expectStatus().isOk();

        restTestClient
                .post()
                .uri(path)
                .body("What's my name?")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("Bond");
                });

        restTestClient
                .post()
                .uri(path)
                .body("I was counting on your discretion. Please, do not share my name!")
                .exchange()
                .expectStatus().isOk();

        restTestClient
                .post()
                .uri(path)
                .body("What's my name?")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("Bond");
                });

        restTestClient
                .post()
                .uri(path)
                .body("I was counting on your discretion. Please, do not share my name!")
                .exchange()
                .expectStatus().isOk();

        restTestClient
                .post()
                .uri(path)
                .body("What did I just tell you?")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("name");
                });
    }

}
