package com.thomasvitale.ai.spring.model;

import com.thomasvitale.ai.spring.ArtistInfo;
import com.thomasvitale.ai.spring.ArtistInfoVariant;
import com.thomasvitale.ai.spring.MusicQuestion;
import org.springframework.ai.chat.model.ChatModel;
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
                Tell me the names of three musicians famous for playing the {instrument} in a {genre} band.
                {format}
                """);
        Map<String,Object> model = Map.of("instrument", question.instrument(), "genre", question.genre(), "format", outputConverter.getFormat());
        var prompt = userPromptTemplate.create(model, OpenAiChatOptions.builder()
                .withResponseFormat(new OpenAiApi.ChatCompletionRequest.ResponseFormat(OpenAiApi.ChatCompletionRequest.ResponseFormat.Type.JSON_OBJECT))
                .build());

        var chatResponse = chatModel.call(prompt);
        return outputConverter.convert(chatResponse.getResult().getOutput().getContent());
    }

    Map<String,Object> chatWithMapOutput() {
        var outputConverter = new MapOutputConverter();
        var userPromptTemplate = new PromptTemplate("""
                For each letter in the RGB color scheme, tell me what it stands for.
                Example: R -> Red.
                {format}
                """);
        Map<String,Object> model = Map.of( "format", outputConverter.getFormat());
        var prompt = userPromptTemplate.create(model);

        var chatResponse = chatModel.call(prompt);
        return outputConverter.convert(chatResponse.getResult().getOutput().getContent());
    }

    List<String> chatWithListOutput(MusicQuestion question) {
        var outputConverter = new ListOutputConverter(new DefaultConversionService());
        var userPromptTemplate = new PromptTemplate("""
                Tell me the names of three musicians famous for playing the {instrument} in a {genre} band.
                {format}
                """);
        Map<String,Object> model = Map.of("instrument", question.instrument(), "genre", question.genre(), "format", outputConverter.getFormat());
        var prompt = userPromptTemplate.create(model);

        var chatResponse = chatModel.call(prompt);
        return outputConverter.convert(chatResponse.getResult().getOutput().getContent());
    }

    ArtistInfoVariant chatWithJsonOutput(MusicQuestion question) {
        var outputConverter = new BeanOutputConverter<>(ArtistInfoVariant.class);
        var userPromptTemplate = new PromptTemplate("""
                Tell me the names of three musicians famous for playing the {instrument} in a {genre} band.
                """);
        Map<String,Object> model = Map.of("instrument", question.instrument(), "genre", question.genre());
        var prompt = userPromptTemplate.create(model, OpenAiChatOptions.builder()
                .withModel("gpt-4o-2024-08-06")
                .withResponseFormat(new OpenAiApi.ChatCompletionRequest.ResponseFormat(OpenAiApi.ChatCompletionRequest.ResponseFormat.Type.JSON_SCHEMA, outputConverter.getJsonSchema()))
                .build());

        var chatResponse = chatModel.call(prompt);
        return outputConverter.convert(chatResponse.getResult().getOutput().getContent());
    }

}
