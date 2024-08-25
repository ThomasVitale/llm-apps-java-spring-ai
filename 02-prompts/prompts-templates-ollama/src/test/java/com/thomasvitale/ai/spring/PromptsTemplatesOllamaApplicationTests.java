package com.thomasvitale.ai.spring;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "60s")
@Import(TestcontainersConfiguration.class)
class PromptsTemplatesOllamaApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/chat/system", "/model/chat/system", "/chat/external", "/model/chat/external"})
    void chat(String path) {
        webTestClient
                .post()
                .uri(path)
                .bodyValue("What is the capital of Italy?")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("Rome");
                });
    }

}
