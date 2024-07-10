package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChatController {

    private final MistralAiChatModel mistralAiChatModel;
    private final OpenAiChatModel openAiChatModel;

    ChatController(MistralAiChatModel mistralAiChatModel, OpenAiChatModel openAiChatModel) {
        this.mistralAiChatModel = mistralAiChatModel;
        this.openAiChatModel = openAiChatModel;
    }

    @GetMapping("/chat/mistral")
    String chatMistralAi(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return mistralAiChatModel.call(message);
    }

    @GetMapping("/chat/openai")
    String chatOpenAi(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return openAiChatModel.call(message);
    }

    @GetMapping("/chat/mistral-options")
    String chatWithMistralAiOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return mistralAiChatModel.call(new Prompt(message, MistralAiChatOptions.builder()
                        .withModel("open-mixtral-8x7b")
                        .withTemperature(1.0f)
                        .build()))
                .getResult().getOutput().getContent();
    }

    @GetMapping("/chat/openai-options")
    String chatWithOpenAiOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return openAiChatModel.call(new Prompt(message, OpenAiChatOptions.builder()
                        .withModel("gpt-4-turbo")
                        .withTemperature(1.0f)
                        .build()))
                .getResult().getOutput().getContent();
    }

}
