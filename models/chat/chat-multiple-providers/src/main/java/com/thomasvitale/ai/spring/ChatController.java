package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private final ChatClient mistralAichatClient;
    private final ChatClient openAichatClient;

    ChatController(MistralAiChatModel mistralAiChatModel, OpenAiChatModel openAiChatModel) {
        this.mistralAichatClient = ChatClient.builder(mistralAiChatModel).build();
        this.openAichatClient = ChatClient.builder(openAiChatModel).build();
    }

    @GetMapping("/chat/mistral-ai")
    String chatMistralAi(String question) {
        return mistralAichatClient
                .prompt(question)
                .call()
                .content();
    }

    @GetMapping("/chat/openai")
    String chatOpenAi(String question) {
        return openAichatClient
                .prompt(question)
                .call()
                .content();
    }

    @GetMapping("/chat/mistral-ai-options")
    String chatMistralAiOptions(String question) {
        return mistralAichatClient
                .prompt(question)
                .options(MistralAiChatOptions.builder()
                        .model(MistralAiApi.ChatModel.SMALL.getValue())
                        .temperature(1.0)
                        .build())
                .call()
                .content();
    }

    @GetMapping("/chat/openai-options")
    String chatOpenAiOptions(String question) {
        return openAichatClient
                .prompt(question)
                .options(OpenAiChatOptions.builder()
                        .model(OpenAiApi.ChatModel.GPT_4_O_MINI.getValue())
                        .temperature(1.0)
                        .build())
                .call()
                .content();
    }

}
