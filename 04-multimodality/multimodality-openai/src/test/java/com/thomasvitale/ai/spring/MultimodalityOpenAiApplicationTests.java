package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "60s")
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class MultimodalityOpenAiApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/chat/image/file", "/model/chat/image/file"})
    void chatFromImageFile(String path) {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("question", "What do you see in this picture? Give a short answer")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                   assertThat(result).containsIgnoringCase("cat");
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"/chat/image/url", "/model/chat/image/url"})
    void chatFromImageUrl(String path) {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("question", "What do you see in this picture? Give a short answer")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("dice");
                });
    }

}
