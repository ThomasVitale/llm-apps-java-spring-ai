package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private final ChatClient chatClient;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemMessageResource;

    ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/chat/user")
    String chatUserMessageTemplate(@RequestBody MusicQuestion question) {
        var userPromptTemplate = """
                Tell me name and band of three musicians famous for playing in a {genre} band.
                Consider only the musicians that play the {instrument} in that band.
                """;

        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("instrument", question.instrument())
                        .param("genre", question.genre())
                )
                .call()
                .content();
    }

    @PostMapping("/chat/system")
    String chatSystemMessageTemplate(@RequestBody String question) {
        var systemPromptTemplate = """
                You are a helpful assistant that always replies starting with {greeting}.
                """;

        return chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(systemPromptTemplate)
                        .param("greeting", randomGreeting())
                )
                .user(question)
                .call()
                .content();
    }

    @PostMapping("/chat/external")
    String chatSystemMessageTemplateExternal(@RequestBody String question) {
        return chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(systemMessageResource)
                        .param("greeting", randomGreeting())
                )
                .user(question)
                .call()
                .content();
    }

    private String randomGreeting() {
        var names = List.of("Howdy", "Ahoy", "Well, well, well");
        return names.get(new Random().nextInt(names.size()));
    }

}
