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
class ChatModelsMultipleProvidersApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/chat/mistral-ai", "/model/chat/mistral-ai"})
    @EnabledIfEnvironmentVariable(named = "SPRING_AI_MISTRALAI_API_KEY", matches = ".*")
    void chatMistralAi(String path) {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("question", "What is the capital of Italy?")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("Rome");
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"/chat/openai", "/model/chat/openai"})
    @EnabledIfEnvironmentVariable(named = "SPRING_AI_OPENAI_API_KEY", matches = ".*")
    void chatOpenAI(String path) {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("question", "What is the capital of Italy?")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("Rome");
                });
    }

}
