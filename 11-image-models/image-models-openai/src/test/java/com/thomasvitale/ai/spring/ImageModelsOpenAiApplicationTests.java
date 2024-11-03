package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "60s")
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class ImageModelsOpenAiApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/image", "/image/provider-options"})
    void image(String path) {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("message", "Here comes the sun")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(URI.create(result)).isNotNull();
                });
    }

}
