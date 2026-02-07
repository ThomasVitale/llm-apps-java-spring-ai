package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@Disabled
class ToolCallingOllamaApplicationTests {

    @Autowired
    RestTestClient restTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/chat/function", "/model/chat/function", "/chat/function/explicit", "/model/chat/function/explicit"})
    void chat(String path) {
        restTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("authorName", "Philip Pullman")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("His Dark Materials");
                });
    }

}
