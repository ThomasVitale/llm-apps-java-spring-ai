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
class GuardrailsOutputApplicationTests {

    @Autowired
    RestTestClient restTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/guardrails/output"})
    void chat(String path) {
        restTestClient
                .post()
                .uri(path)
                .body(new MusicQuestion("rock", "piano"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArtistInfo.class).value(result -> {
                    assertThat(result).isNotNull();
                    assertThat(result.name()).isNotEmpty();
                    assertThat(result.band()).isNotEmpty();
                });
    }

}
