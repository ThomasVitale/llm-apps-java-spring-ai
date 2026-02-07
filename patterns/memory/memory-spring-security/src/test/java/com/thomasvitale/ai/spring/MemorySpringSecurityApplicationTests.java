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
class MemorySpringSecurityApplicationTests {

    @Autowired
    RestTestClient restTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/memory/security"})
    void chat(String path) {
        var restTestClient1 = restTestClient.mutate()
                .defaultHeaders(headers -> headers.setBasicAuth("george", "lizard"))
                .build();

        restTestClient1
                .post()
                .uri(path)
                .body("My name is Bond. James Bond.")
                .exchange()
                .expectStatus().isOk();

        restTestClient1
                .post()
                .uri(path)
                .body("What's my name?")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("Bond");
                });

        var restTestClient2 = restTestClient.mutate()
                .defaultHeaders(headers -> headers.setBasicAuth("isabella", "butterfly"))
                .build();

        restTestClient2
                .post()
                .uri(path)
                .body("What's my name?")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).doesNotContainIgnoringCase("Bond");
                });

    }

}
