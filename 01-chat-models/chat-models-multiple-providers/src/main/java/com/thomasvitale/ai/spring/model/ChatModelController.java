package com.thomasvitale.ai.spring.model;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Chat examples using the low-level ChatModel API.
 */
@RestController
@RequestMapping("/model")
class ChatModelController {

    private final MistralAiChatModel mistralAiChatModel;
    private final OpenAiChatModel openAiChatModel;

    ChatModelController(MistralAiChatModel mistralAiChatModel, OpenAiChatModel openAiChatModel) {
        this.mistralAiChatModel = mistralAiChatModel;
        this.openAiChatModel = openAiChatModel;
    }

    @GetMapping("/chat/mistral-ai")
    String chatMistralAi(String question) {
        return mistralAiChatModel.call(question);
    }

    @GetMapping("/chat/openai")
    String chatOpenAi(String question) {
        return openAiChatModel.call(question);
    }

    @GetMapping("/chat/mistral-ai-options")
    String chatWithMistralAiOptions(String question) {
        return mistralAiChatModel.call(new Prompt(question, MistralAiChatOptions.builder()
                        .withModel(MistralAiApi.ChatModel.OPEN_MIXTRAL_7B.getValue())
                        .withTemperature(1.0)
                        .build()))
                .getResult().getOutput().getContent();
    }

    @GetMapping("/chat/openai-options")
    String chatWithOpenAiOptions(String question) {
        return openAiChatModel.call(new Prompt(question, OpenAiChatOptions.builder()
                        .withModel(OpenAiApi.ChatModel.GPT_4_O_MINI.getValue())
                        .withTemperature(1.0)
                        .build()))
                .getResult().getOutput().getContent();
    }

}
