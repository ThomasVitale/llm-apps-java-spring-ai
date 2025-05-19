package com.thomasvitale.ai.spring;

import com.thomasvitale.ai.spring.guardrails.JsonOutputGuardrailAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class JsonOutputGuardrail {

    private final ChatClient chatClient;

    JsonOutputGuardrail(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.clone()
                .defaultAdvisors(JsonOutputGuardrailAdvisor.builder()
                        .chatClientBuilder(chatClientBuilder.clone())
                        .type(ArtistInfo.class)
                        .build())
                .build();
    }

    @PostMapping("/guardrails/output")
    ArtistInfo chat(@RequestBody MusicQuestion question) {
        var userPromptTemplate = """
                Tell me the names of one musician famous for playing the {instrument} in a {genre} band.
                """;

        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("genre", question.genre())
                        .param("instrument", question.instrument())
                )
                .call()
                .entity(ArtistInfo.class);
    }

}
