package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ClassificationService {

    private final ChatClient chatClient;

    ClassificationService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    String classifySimple(String message) {
        return chatClient
                .prompt()
                .user(userSpec -> userSpec.text("""
                    Classify the type of the provided text and return the classification type as "BOOK" OR "MUSIC".
            
                    {text}
                    """)
                    .param("text", message))
                .call()
                .content();
    }

    ClassificationType classifyStructured(String message) {
        return chatClient
                .prompt()
                .user(userSpec -> userSpec.text("""
                    Classify the type of the provided text and return the classification type.
                    ---------------------
                    TEXT:
                    {text}
                    ---------------------
                    """)
                        .param("text", message))
                .user(message)
                .call()
                .entity(ClassificationType.class);
    }

    ClassificationType classifyFewShotsPrompt(String message) {
        return chatClient
                .prompt()
                .user(userSpec -> userSpec.text("""
                    Classify the type of the provided text and return the classification type.

                    For example:
        
                    Input: The celesta is a good instrument for fantasy or mystery compositions, creating a sense of mystical and supernatural.
                    Output: "MUSIC"
        
                    Input: In "The Lord of The Rings", Tolkien wrote the famous words: "There’s some good in this world, Mr. Frodo… and it’s worth fighting for.".
                    Output: "BOOK"
        
                    Input: They're taking the hobbits to Isengard! To Isengard! To Isengard!
                    Output: "UNKNOWN"
        
                    ---------------------
                    TEXT:
                    {text}
                    ---------------------
                    """)
                        .param("text", message))
                .call()
                .entity(ClassificationType.class);
    }

    ClassificationType classifyFewShotsHistory(String message) {
        return chatClient
                .prompt()
                .messages(getPromptWithFewShotsHistory(message))
                .call()
                .entity(ClassificationType.class);
    }

    private List<Message> getPromptWithFewShotsHistory(String message) {
        return List.of(
                new SystemMessage("""
                    Classify the type of the provided text and return the classification type.
                    """),
                new UserMessage("""
                    The celesta is a good instrument for fantasy or mystery compositions, creating a sense of mystical and supernatural.
                    """),
                new AssistantMessage("MUSIC"),
                new UserMessage(""" 
                        In "The Lord of The Rings", Tolkien wrote the famous words: "There’s some good in this world, Mr. Frodo… and it’s worth fighting for.".
                        """),
                new AssistantMessage("BOOK"),
                new UserMessage("""
                        They're taking the hobbits to Isengard! To Isengard! To Isengard!
                        """),
                new AssistantMessage("UNKNOWN"),
                new UserMessage(message)
        );
    }

}
