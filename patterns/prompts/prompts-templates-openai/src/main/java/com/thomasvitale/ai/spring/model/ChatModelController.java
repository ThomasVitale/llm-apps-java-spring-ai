package com.thomasvitale.ai.spring.model;

import com.thomasvitale.ai.spring.MusicQuestion;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Chat examples using the low-level ChatModel API.
 */
@RestController
@RequestMapping("/model")
class ChatModelController {

    private final ChatModel chatModel;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemMessageResource;

    ChatModelController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/chat/user")
    String chatUserMessageTemplate(@RequestBody MusicQuestion question) {
        var userPromptTemplate = new PromptTemplate("""
                Tell me name and band of three musicians famous for playing in a {genre} band.
                Consider only the musicians that play the {instrument} in that band.
                """);
        Map<String,Object> model = Map.of(
                "instrument", question.instrument(),
                "genre", question.genre());

        var prompt = userPromptTemplate.create(model);

        var chatResponse = chatModel.call(prompt);
        return chatResponse.getResult().getOutput().getText();
    }

    @PostMapping("/chat/system")
    String chatSystemMessageTemplate(@RequestBody String question) {
        var systemPromptTemplate = new SystemPromptTemplate("""
                You are a helpful assistant that always replies starting with {greeting}.
                """);
        Map<String,Object> model = Map.of("greeting", randomGreeting());
        var systemMessage = systemPromptTemplate.createMessage(model);
        var userMessage = new UserMessage(question);

        var prompt = new Prompt(List.of(systemMessage, userMessage));

        var chatResponse = chatModel.call(prompt);
        return chatResponse.getResult().getOutput().getText();
    }

    @PostMapping("/chat/external")
    String chatSystemMessageTemplateExternal(@RequestBody String question) {
        var systemPromptTemplate = new SystemPromptTemplate(systemMessageResource);
        Map<String,Object> model = Map.of("greeting", randomGreeting());
        var systemMessage = systemPromptTemplate.createMessage(model);
        var userMessage = new UserMessage(question);

        var prompt = new Prompt(List.of(systemMessage, userMessage));

        var chatResponse = chatModel.call(prompt);
        return chatResponse.getResult().getOutput().getText();
    }

    private String randomGreeting() {
        var names = List.of("Howdy", "Ahoy", "Well, well, well");
        return names.get(new Random().nextInt(names.size()));
    }

}
