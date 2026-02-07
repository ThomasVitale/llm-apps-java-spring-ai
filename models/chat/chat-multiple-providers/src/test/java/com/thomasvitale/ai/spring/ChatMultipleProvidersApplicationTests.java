package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class ChatMultipleProvidersApplicationTests {

    @Autowired
    RestTestClient restTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/chat/mistral-ai", "/model/chat/mistral-ai", "/chat/mistral-ai-options", "/model/chat/mistral-ai-options"})
    @EnabledIfEnvironmentVariable(named = "MISTRAL_AI_API_KEY", matches = ".*")
    void chatMistralAi(String path) {
        restTestClient
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
    @ValueSource(strings = {"/chat/openai", "/model/chat/openai", "/chat/openai-options", "/model/chat/openai-options"})
    @EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
    void chatOpenAI(String path) {
        restTestClient
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
