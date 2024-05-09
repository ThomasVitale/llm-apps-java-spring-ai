package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
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
        var outputConverter = new BeanOutputConverter<>(ArtistInfo.class);

        var userPromptTemplate = new PromptTemplate("""
                Tell me name and band of one musician famous for playing in a {genre} band.
                Consider only the musicians that play the {instrument} in that band.
                {format}
                """);
        Map<String,Object> model = Map.of("instrument", question.instrument(), "genre", question.genre(), "format", outputConverter.getFormat());
        var userMessage = userPromptTemplate.createMessage(model);

        var chatResponse = chatClient.call(new Prompt(userMessage, OpenAiChatOptions.builder()
                .withResponseFormat(new OpenAiApi.ChatCompletionRequest.ResponseFormat("json_object"))
                .build()));
        return outputConverter.convert(chatResponse.getResult().getOutput().getContent());
    }

    Map<String,Object> chatWithMapOutput(MusicQuestion question) {
        var outputConverter = new MapOutputConverter();

        var userPromptTemplate = new PromptTemplate("""
                Tell me name and band of one musician famous for playing in a {genre} band.
                Consider only the musicians that play the {instrument} in that band.
                {format}
                """);
        Map<String,Object> model = Map.of("instrument", question.instrument(), "genre", question.genre(), "format", outputConverter.getFormat());
        var userMessage = userPromptTemplate.createMessage(model);

        var chatResponse = chatClient.call(new Prompt(userMessage, OpenAiChatOptions.builder()
                .withResponseFormat(new OpenAiApi.ChatCompletionRequest.ResponseFormat("json_object"))
                .build()));
        return outputConverter.convert(chatResponse.getResult().getOutput().getContent());
    }

    List<String> chatWithListOutput(MusicQuestion question) {
        var outputConverter = new ListOutputConverter(new DefaultConversionService());

        var userPromptTemplate = new PromptTemplate("""
                Tell me the names of three musicians famous for playing in a {genre} band.
                Consider only the musicians that play the {instrument} in that band.
                {format}
                """);
        Map<String,Object> model = Map.of("instrument", question.instrument(), "genre", question.genre(), "format", outputConverter.getFormat());
        var userMessage = userPromptTemplate.createMessage(model);

        var result = chatClient.call(userMessage);
        return outputConverter.convert(result);
    }

}
