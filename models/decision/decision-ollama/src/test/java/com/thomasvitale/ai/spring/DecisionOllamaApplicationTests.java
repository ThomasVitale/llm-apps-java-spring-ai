package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class DecisionOllamaApplicationTests {

    private static final String DANGEROUS_SITUATION = "A wizard stands on a narrow bridge. A demon made of flame, immune to fire, is about to cross it.";

    private static final String SAFE_SITUATION = "The wizard sits by the fire in a cosy inn, enjoying a pipe. Nobody is around and nothing threatens him.";

    @Autowired
    RestTestClient restTestClient;

    @Test
    void decision() {
        restTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/decision")
                        .queryParam("situation", "{situation}")
                        .build(DANGEROUS_SITUATION))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.spell.value").isEqualTo("barrier")
                .jsonPath("$.danger.yes").isEqualTo(true)
                .jsonPath("$.drama.nearestLevel").isEqualTo(1);
    }

    @Test
    void decisionThreshold() {
        assertThat(callThreshold(DANGEROUS_SITUATION)).startsWith("Run, you fools!");
        assertThat(callThreshold(SAFE_SITUATION)).startsWith("Keep calm");
    }

    @Test
    void decisionFields() {
        restTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/decision/fields")
                        .queryParam("traveller", "Frodo")
                        .queryParam("destination", "{destination}")
                        .queryParam("companions", 1)
                        .build("Mount Doom"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.risk.nearestLevel").isNumber()
                .jsonPath("$.escort.yes").isEqualTo(true);
    }

    @Test
    void decisionConversation() {
        restTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/decision/conversation")
                        .queryParam("question", "{question}")
                        .queryParam("answer", "{answer}")
                        .build("Who forged the One Ring?", "Stop bothering me with your silly questions."))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.answered.yes").isEqualTo(false)
                .jsonPath("$.tone.value").isEqualTo("rude");
    }

    private String callThreshold(String situation) {
        return restTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/decision/threshold")
                        .queryParam("situation", "{situation}")
                        .build(situation))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }

}
