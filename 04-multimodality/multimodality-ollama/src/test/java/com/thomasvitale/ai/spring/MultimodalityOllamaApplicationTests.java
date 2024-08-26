package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "120s")
@Import(TestcontainersConfiguration.class)
@Disabled
class MultimodalityOllamaApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void chatFromImageFile() {
        webTestClient
                .get()
                .uri("/chat/image/file")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                   assertThat(result).containsIgnoringCase("cat");
                });
    }

}
