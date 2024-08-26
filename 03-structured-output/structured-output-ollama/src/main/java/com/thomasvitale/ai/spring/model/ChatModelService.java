package com.thomasvitale.ai.spring.model;

import com.thomasvitale.ai.spring.ArtistInfo;
import com.thomasvitale.ai.spring.MusicQuestion;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Chat examples using the low-level ChatModel API.
 */
@Service
class ChatModelService {

    private final ChatModel chatModel;

    ChatModelService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    ArtistInfo chatWithBeanOutput(MusicQuestion question) {
        var outputConverter = new BeanOutputConverter<>(ArtistInfo.class);
        var userPromptTemplate = new PromptTemplate("""
                Tell me name and band of one musician famous for playing in a {genre} band.
                Consider only the musicians that play the {instrument} in that band.
                {format}
                """);
        Map<String,Object> model = Map.of("instrument", question.instrument(), "genre", question.genre(), "format", outputConverter.getFormat());
        var prompt = userPromptTemplate.create(model, OllamaOptions.builder()
                .withFormat("json")
                .build());

        var chatResponse = chatModel.call(prompt);
        return outputConverter.convert(chatResponse.getResult().getOutput().getContent());
    }

    Map<String,Object> chatWithMapOutput(MusicQuestion question) {
        var outputConverter = new MapOutputConverter();
        var userPromptTemplate = new PromptTemplate("""
                Tell me the names of three musicians famous for playing in a {genre} band.
                Consider only the musicians that play the {instrument} in that band.
                {format}
                """);
        Map<String,Object> model = Map.of("instrument", question.instrument(), "genre", question.genre(), "format", outputConverter.getFormat());
        var prompt = userPromptTemplate.create(model);

        var chatResponse = chatModel.call(prompt);
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
        var prompt = userPromptTemplate.create(model);

        var chatResponse = chatModel.call(prompt);
        return outputConverter.convert(chatResponse.getResult().getOutput().getContent());
    }

}
