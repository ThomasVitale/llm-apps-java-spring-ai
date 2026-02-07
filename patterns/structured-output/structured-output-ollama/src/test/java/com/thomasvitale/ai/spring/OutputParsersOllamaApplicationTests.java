package com.thomasvitale.ai.spring;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class OutputParsersOllamaApplicationTests {

    @Autowired
    RestTestClient restTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/chat/bean", "/model/chat/bean"})
    void bean(String path) {
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

    @ParameterizedTest
    @ValueSource(strings = {"/chat/map", "/model/chat/map"})
    void map(String path) {
        restTestClient
                .post()
                .uri(path)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Map<String,Object>>() {}).value(result -> {
                    assertThat(result).hasSize(3);
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"/chat/list", "/model/chat/list"})
    void list(String path) {
        restTestClient
                .post()
                .uri(path)
                .body(new MusicQuestion("rock", "piano"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<String>>() {}).value(result -> {
                    assertThat(result).hasSize(3);
                });
    }

}
