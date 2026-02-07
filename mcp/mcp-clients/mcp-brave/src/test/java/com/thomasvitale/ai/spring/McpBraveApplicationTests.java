package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@EnabledIfEnvironmentVariable(named = "BRAVE_SEARCH_API_KEY", matches = ".*")
class McpBraveApplicationTests {

    @Autowired
    RestTestClient restTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/chat/mcp"})
    void chat(String path) {
        restTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("question", "Does Spring AI supports integrations with Ollama?")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }

}
