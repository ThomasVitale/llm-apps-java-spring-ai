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
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class PromptsMessagesOpenAiApplicationTests {

    @Autowired
    RestTestClient restTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/chat/single", "/model/chat/single", "/chat/multiple", "/model/chat/multiple", "/chat/external", "/model/chat/external"})
    void chat(String path) {
        restTestClient
                .post()
                .uri(path)
                .body("What is the capital of Italy?")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("Rome");
                });
    }

}
