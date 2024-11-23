package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class StructuredDataExtractionController {

    private final ChatClient chatClient;

    StructuredDataExtractionController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultOptions(ChatOptionsBuilder.builder()
                        .withTemperature(0.0)
                        .build())
                .build();
    }

    @PostMapping("/extract")
    PatientJournal extract(@RequestBody String message) {
        return chatClient
                .prompt()
                .user(userSpec -> userSpec.text("""
                    Extract structured data from the provided text.
                    If you do not know the value of a field asked to extract,
                    do not include any value for the field in the result.
        
                    ---------------------
                    TEXT:
                    {text}
                    ---------------------
                    """)
                        .param("text", message))
                .call()
                .entity(PatientJournal.class);
    }

}
