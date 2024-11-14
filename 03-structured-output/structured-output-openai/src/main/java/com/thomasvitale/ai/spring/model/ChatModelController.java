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
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Chat examples using the low-level ChatModel API.
 */
@RestController
@RequestMapping("/model")
class ChatModelController {

    private final ChatModel chatModel;

    ChatModelController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/chat/bean")
    ArtistInfo chatBeanOutput(@RequestBody MusicQuestion question) {
        var outputConverter = new BeanOutputConverter<>(ArtistInfo.class);
        var userPromptTemplate = new PromptTemplate("""
                Tell me the name of one musician famous for playing the {instrument} in a {genre} band.
                {format}
                """);
        Map<String,Object> model = Map.of(
                "instrument", question.instrument(),
                "genre", question.genre(),
                "format", outputConverter.getFormat());
        var prompt = userPromptTemplate.create(model, OpenAiChatOptions.builder()
                .withResponseFormat(new ResponseFormat(ResponseFormat.Type.JSON_OBJECT, null))
                .build());

        var chatResponse = chatModel.call(prompt);
        return outputConverter.convert(chatResponse.getResult().getOutput().getContent());
    }

    @PostMapping("/chat/map")
    Map<String,Object> chatMapOutput() {
        var outputConverter = new MapOutputConverter();
        var userPromptTemplate = new PromptTemplate("""
                For each letter in the RGB color scheme, tell me what it stands for.
                Example: R -> Red.
                {format}
                """);
        Map<String,Object> model = Map.of("format", outputConverter.getFormat());
        var prompt = userPromptTemplate.create(model);

        var chatResponse = chatModel.call(prompt);
        return outputConverter.convert(chatResponse.getResult().getOutput().getContent());
    }

    @PostMapping("/chat/list")
    List<String> chatListOutput(@RequestBody MusicQuestion question) {
        var outputConverter = new ListOutputConverter(new DefaultConversionService());
        var userPromptTemplate = new PromptTemplate("""
                Tell me the names of three musicians famous for playing the {instrument} in a {genre} band.
                {format}
                """);
        Map<String,Object> model = Map.of(
                "instrument", question.instrument(),
                "genre", question.genre(),
                "format", outputConverter.getFormat());
        var prompt = userPromptTemplate.create(model);

        var chatResponse = chatModel.call(prompt);
        return outputConverter.convert(chatResponse.getResult().getOutput().getContent());
    }

    @PostMapping("/chat/json")
    ArtistInfoVariant chatJsonOutput(@RequestBody MusicQuestion question) {
        var outputConverter = new BeanOutputConverter<>(ArtistInfoVariant.class);
        var userPromptTemplate = new PromptTemplate("""
                Tell me the name of one musician famous for playing the {instrument} in a {genre} band.
                """);
        Map<String,Object> model = Map.of("instrument", question.instrument(), "genre", question.genre());
        var prompt = userPromptTemplate.create(model, OpenAiChatOptions.builder()
                .withModel(OpenAiApi.ChatModel.GPT_4_O.getValue())
                .withResponseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, outputConverter.getJsonSchema()))
                .build());

        var chatResponse = chatModel.call(prompt);
        return outputConverter.convert(chatResponse.getResult().getOutput().getContent());
    }

}
