package com.thomasvitale.ai.spring;

import com.thomasvitale.ai.spring.guardrails.DecisionOutputGuardrailAdvisor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.decision.DecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class DecisionOllamaApplicationTests {

    private static final String LATE_DELIVERY = "My order 48213 was supposed to arrive last week and the tracking page hasn't changed since Monday.";

    private static final String RECIPE = "What is a good recipe for a vegetarian lasagna?";

    @Autowired
    RestTestClient restTestClient;

    @Autowired
    DecisionModel decisionModel;

    @Test
    void onTopic() {
        assertYesNo("/tickets/on-topic", LATE_DELIVERY, true);
        assertYesNo("/tickets/on-topic", RECIPE, false);
        assertYesNo("/tickets/on-topic", "Who composed the Moonlight Sonata?", false);
    }

    @Test
    void onTopicWithThreshold() {
        assertOnTopicWithThreshold(0.5, true);
        assertOnTopicWithThreshold(1.1, false);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            I was charged twice for the same order, please fix my invoice.         | billing
            My order 48213 hasn't arrived and the tracking page hasn't changed.    | shipping
            I would like to send back the violin I bought, it's the wrong size.    | returns
            My new guitar amplifier keeps cutting out every few minutes.           | technical
            I can't log in, the password reset email never arrives.                | account
            Do you have any job openings at the store?                             | other
            """)
    void category(String ticket, String category) {
        restTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tickets/category")
                        .queryParam("text", "{text}")
                        .build(ticket))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.value").isEqualTo(category)
                .jsonPath("$.probabilities.length()").isEqualTo(6)
                .jsonPath("$.rankedOptions[0]").isEqualTo(category);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            Thank you so much, the new saxophone is wonderful and arrived a day early!             | 3
            Please send me a copy of the invoice for order 48213.                                  | 2
            This is the second time my delivery is late. I'm disappointed, please look into it.    | 1
            This is OUTRAGEOUS! You useless people lost my order AGAIN! Absolutely unacceptable!   | 0
            """)
    void sentiment(String ticket, int level) {
        restTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tickets/sentiment")
                        .queryParam("text", "{text}")
                        .build(ticket))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.nearestLevel").isEqualTo(level)
                .jsonPath("$.probabilities.length()").isEqualTo(4);
    }

    @Test
    void triageRoutesToTheCategoryQueue() {
        restTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tickets/triage")
                        .queryParam("subject", "{subject}")
                        .queryParam("body", "{body}")
                        .queryParam("customer", "individual")
                        .build("Amplifier cuts out", "My new guitar amplifier keeps cutting out every few minutes. How can I fix it?"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.queue").isEqualTo("technical")
                .jsonPath("$.category").isEqualTo("technical");
    }

    @Test
    void triageEscalatesAngryCustomers() {
        restTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tickets/triage")
                        .queryParam("subject", "{subject}")
                        .queryParam("body", "{body}")
                        .queryParam("customer", "music-school")
                        .build("Duplicate charge, cancelling", "You charged our music school twice for order 48213 and nobody replies. We are closing our account and moving to another supplier."))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.queue").isEqualTo("escalated")
                .jsonPath("$.category").isEqualTo("billing")
                .jsonPath("$.sentiment").isEqualTo("angry");
    }

    @Test
    void inputGuardrailBlocksOffTopicMessages() {
        assertThat(chat(RECIPE)).startsWith("I can only help with questions about our store");
    }

    @Test
    void inputGuardrailBlocksSensitiveData() {
        assertThat(chat("Please refund order 48213 to my credit card 4111 1111 1111 1111, CVV 123."))
                .startsWith("For your safety");
    }

    @Test
    void guardrailsLetAnsweredQuestionsThrough() {
        assertThat(chat("How long do I have to return an instrument?")).contains("30");
    }

    @Test
    void outputGuardrailHandsOffQuestionsThePolicyDoesNotCover() {
        assertThat(chat("Do you ship to Iceland?")).isEqualTo(DecisionOutputGuardrailAdvisor.HANDOFF_RESPONSE);
    }

    @Test
    void outputGuardrailReplacesAReplyThatDodgesTheQuestion() {
        assertThat(callWithReply("How long do I have to return an instrument?", "I'm not able to help with that."))
                .isEqualTo(DecisionOutputGuardrailAdvisor.HANDOFF_RESPONSE);
    }

    @Test
    void outputGuardrailKeepsAReplyThatAnswersTheQuestion() {
        var reply = "You can return an instrument within 30 days of delivery, in its original packaging.";
        assertThat(callWithReply("How long do I have to return an instrument?", reply)).isEqualTo(reply);
    }

    private void assertOnTopicWithThreshold(double threshold, boolean expected) {
        restTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tickets/on-topic/threshold")
                        .queryParam("text", "{text}")
                        .queryParam("threshold", threshold)
                        .build(LATE_DELIVERY))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class).isEqualTo(expected);
    }

    private void assertYesNo(String path, String text, boolean expected) {
        restTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("text", "{text}")
                        .build(text))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.yes").isEqualTo(expected)
                .jsonPath("$.probability").isNumber();
    }

    private String chat(String question) {
        return restTestClient
                .post()
                .uri("/support/chat")
                .body(question)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }

    private String callWithReply(String question, String reply) {
        ChatModel chatModel = _ -> ChatResponse.builder()
                .generations(List.of(new Generation(AssistantMessage.builder().content(reply).build())))
                .build();
        return ChatClient.builder(chatModel)
                .defaultAdvisors(new DecisionOutputGuardrailAdvisor(decisionModel))
                .build()
                .prompt()
                .user(question)
                .call()
                .content();
    }

}
