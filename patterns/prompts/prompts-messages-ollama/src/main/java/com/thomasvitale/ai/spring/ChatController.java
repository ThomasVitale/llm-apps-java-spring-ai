package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private final ChatClient chatClient;

    private final Resource systemMessageResource;

    ChatController(ChatClient.Builder chatClientBuilder, @Value("classpath:/prompts/system-message.st") Resource systemMessageResource) {
        this.chatClient = chatClientBuilder.build();
        this.systemMessageResource = systemMessageResource;
    }

    @PostMapping("/chat/single")
    String chatSingleMessage(@RequestBody String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    @PostMapping("/chat/multiple")
    String chatMultipleMessages(@RequestBody String question) {
        return chatClient.prompt()
                .system("""
                    You are a helpful and polite assistant.
                    Answer in one sentence using a very formal language
                    and starting the answer with a formal greeting.
                    """)
                .user(question)
                .call()
                .content();
    }

    @PostMapping("/chat/external")
    String chatExternalMessage(@RequestBody String question) {
        return chatClient.prompt()
                .system(systemMessageResource)
                .user(question)
                .call()
                .content();
    }

}
