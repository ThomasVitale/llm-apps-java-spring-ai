package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private final ChatClient chatClient;

    ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/chat/bean")
    ArtistInfo chatBeanOutput(@RequestBody MusicQuestion question) {
        var userPromptTemplate = """
                Tell me the names of one musician famous for playing the {instrument} in a {genre} band.
                """;

        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("genre", question.genre())
                        .param("instrument", question.instrument())
                )
                .options(OllamaOptions.builder()
                        .withFormat("json")
                        .build())
                .call()
                .entity(ArtistInfo.class);
    }

    @PostMapping("/chat/map")
    Map<String,Object> chatMapOutput() {
        return chatClient.prompt("""
                    For each letter in the RGB color scheme, tell me what it stands for.
                    Example: R -> Red.
                    """)
                .call()
                .entity(new MapOutputConverter());
    }

    @PostMapping("/chat/list")
    List<String> chatListOutput(@RequestBody MusicQuestion question) {
        var userPromptTemplate = """
                Tell me the names of three musicians famous for playing the {instrument} in a {genre} band.
                """;

        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("genre", question.genre())
                        .param("instrument", question.instrument())
                )
                .call()
                .entity(new ListOutputConverter(new DefaultConversionService()));
    }

}
