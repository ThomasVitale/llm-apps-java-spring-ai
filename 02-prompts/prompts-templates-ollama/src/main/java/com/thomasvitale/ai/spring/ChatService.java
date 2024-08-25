package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Chat examples using the high-level ChatClient API.
 */
@Service
class ChatService {

    private final ChatClient chatClient;

    private final Resource systemMessageResource;

    ChatService(ChatClient.Builder chatClientBuilder, @Value("classpath:/prompts/system-message.st") Resource systemMessageResource) {
        this.chatClient = chatClientBuilder.build();
        this.systemMessageResource = systemMessageResource;
    }

    String chatWithUserMessageTemplate(MusicQuestion question) {
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

    String chatWithSystemMessageTemplate(String message) {
        var systemPromptTemplate = """
                You are a helpful assistant that always replies starting with {greeting}.
                """;

        return chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(systemPromptTemplate)
                        .param("greeting", randomGreeting())
                )
                .user(message)
                .call()
                .content();
    }

    String chatWithSystemMessageTemplateExternal(String message) {
        return chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(systemMessageResource)
                        .param("greeting", randomGreeting())
                )
                .user(message)
                .call()
                .content();
    }

    private String randomGreeting() {
        var names = List.of("Howdy", "Ahoy", "Well, well, well");
        return names.get(new Random().nextInt(names.size()));
    }

}
