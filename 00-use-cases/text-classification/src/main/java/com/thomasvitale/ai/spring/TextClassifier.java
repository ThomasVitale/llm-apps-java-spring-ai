package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class TextClassifier {

    private final ChatClient chatClient;

    TextClassifier(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultOptions(ChatOptionsBuilder.builder()
                        .withTemperature(0.0f)
                        .build())
                .build();
    }

    String classifyClassNames(String text) {
        return chatClient
                .prompt()
                .system("""
                    Classify the provided text into one of these classes:
                    BUSINESS, SPORT, TECHNOLOGY, OTHER.
                    """)
                .user(text)
                .call()
                .content();
    }

    String classifyClassDescriptions(String text) {
        return chatClient
                .prompt()
                .system("""
                    Classify the provided text into one of these classes.
                    
                    BUSINESS: Financial markets, economic trends, company acquisitions.
                    SPORT: Sport events, teams, players, tournaments, and statistics.
                    TECHNOLOGY: Technological advancements, innovations, software product launches.
                    OTHER: Anything that doesn't fit into the other categories.
                    """)
                .user(text)
                .call()
                .content();
    }

    String classifyFewShotsPrompt(String text) {
        return chatClient
                .prompt()
                .system("""
                    Classify the provided text into one of these classes:
                    BUSINESS, SPORT, TECHNOLOGY, OTHER.
                    
                    Text: Tech companies are all competing in this new AI rush.
                    Class: BUSINESS
                    
                    Text: The semifinals of the UEFA Euro 2024 will be played on Saturday.
                    Class: SPORT
        
                    Text: Apple announced the new version of their operating system.
                    Class: TECHNOLOGY
        
                    Text: The Ravenclaw Quidditch team won the tournament!
                    Class: OTHER
                    """)
                .user(text)
                .call()
                .content();
    }

    String classifyFewShotsHistory(String text) {
        return chatClient
                .prompt()
                .messages(getPromptWithFewShotsHistory(text))
                .call()
                .content();
    }

    ClassificationType classifyStructured(String text) {
        return chatClient
                .prompt()
                .messages(getPromptWithFewShotsHistory(text))
                .call()
                .entity(ClassificationType.class);
    }

    private List<Message> getPromptWithFewShotsHistory(String text) {
        return List.of(
                new SystemMessage("""
                    Classify the provided text into one of these classes:
                    BUSINESS, SPORT, TECHNOLOGY, OTHER.
                    """),
                new UserMessage("Tech companies are all competing in this new AI rush."),
                new AssistantMessage("BUSINESS"),
                new UserMessage("The semifinals of the UEFA Euro 2024 will be played on Saturday."),
                new AssistantMessage("SPORT"),
                new UserMessage("Apple announced the new version of their operating system."),
                new AssistantMessage("TECHNOLOGY"),
                new UserMessage("The Ravenclaw Quidditch team won the tournament!"),
                new AssistantMessage("OTHER"),
                new UserMessage(text)
        );
    }

}
