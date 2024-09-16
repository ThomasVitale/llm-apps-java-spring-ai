package com.thomasvitale.ai.spring.model;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    String chatMistralAi(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return mistralAiChatModel.call(question);
    }

    @GetMapping("/chat/openai")
    String chatOpenAi(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return openAiChatModel.call(question);
    }

    @GetMapping("/chat/mistral-ai-options")
    String chatWithMistralAiOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return mistralAiChatModel.call(new Prompt(question, MistralAiChatOptions.builder()
                        .withModel("open-mixtral-8x7b")
                        .withTemperature(1.0)
                        .build()))
                .getResult().getOutput().getContent();
    }

    @GetMapping("/chat/openai-options")
    String chatWithOpenAiOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return openAiChatModel.call(new Prompt(question, OpenAiChatOptions.builder()
                        .withModel("gpt-4o-mini")
                        .withTemperature(1.0)
                        .build()))
                .getResult().getOutput().getContent();
    }

}
