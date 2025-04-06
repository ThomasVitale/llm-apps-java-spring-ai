package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "180s")
@TestPropertySource(properties = "arconia.dev.services.ollama.enabled=true")
@EnabledIfEnvironmentVariable(named = "BRAVE_API_KEY", matches = ".*")
class McpBraveApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @ParameterizedTest
    @ValueSource(strings = {"/chat/mcp"})
    void chat(String path) {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("question", "Does Spring AI supports integrations with Ollama?")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }

}
