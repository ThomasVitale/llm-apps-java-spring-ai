package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.parser.ListOutputParser;
import org.springframework.ai.parser.MapOutputParser;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
class ChatService {

    private final ChatClient chatClient;

    ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    ArtistInfo chatWithBeanOutput(MusicQuestion question) {
        var outputParser = new BeanOutputParser<>(ArtistInfo.class);

        var userPromptTemplate = new PromptTemplate("""
                Tell me name and band of one musician famous for playing in a {genre} band.
                Consider only the musicians that play the {instrument} in that band.
                {format}
                """);
        Map<String,Object> model = Map.of("instrument", question.instrument(), "genre", question.genre(), "format", outputParser.getFormat());
        var prompt = userPromptTemplate.create(model);

        var chatResponse = chatClient.call(prompt);
        return outputParser.parse(chatResponse.getResult().getOutput().getContent());
    }

    Map<String,Object> chatWithMapOutput(MusicQuestion question) {
        var outputParser = new MapOutputParser();

        var userPromptTemplate = new PromptTemplate("""
                Tell me name and band of one musician famous for playing in a {genre} band.
                Consider only the musicians that play the {instrument} in that band.
                {format}
                """);
        Map<String,Object> model = Map.of("instrument", question.instrument(), "genre", question.genre(), "format", outputParser.getFormat());
        var prompt = userPromptTemplate.create(model);

        var chatResponse = chatClient.call(prompt);
        return outputParser.parse(chatResponse.getResult().getOutput().getContent());
    }

    List<String> chatWithListOutput(MusicQuestion question) {
        var outputParser = new ListOutputParser(new DefaultConversionService());

        var userPromptTemplate = new PromptTemplate("""
                Tell me names of three musicians famous for playing in a {genre} band.
                Consider only the musicians that play the {instrument} in that band.
                {format}
                """);
        Map<String,Object> model = Map.of("instrument", question.instrument(), "genre", question.genre(), "format", outputParser.getFormat());
        var prompt = userPromptTemplate.create(model);

        var chatResponse = chatClient.call(prompt);
        return outputParser.parse(chatResponse.getResult().getOutput().getContent());
    }

}
