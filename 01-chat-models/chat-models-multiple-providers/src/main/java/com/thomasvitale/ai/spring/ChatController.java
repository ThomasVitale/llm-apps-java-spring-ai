package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    String chatMistralAi(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return mistralAichatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    @GetMapping("/chat/openai")
    String chatOpenAi(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return openAichatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    @GetMapping("/chat/mistral-ai-options")
    String chatWithMistralAiOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return mistralAichatClient.prompt()
                .user(question)
                .options(MistralAiChatOptions.builder()
                        .withModel("open-mixtral-8x7b")
                        .withTemperature(1.0f)
                        .build())
                .call()
                .content();
    }

    @GetMapping("/chat/openai-options")
    String chatWithOpenAiOptions(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return openAichatClient.prompt()
                .user(question)
                .options(OpenAiChatOptions.builder()
                        .withModel("gpt-4o-mini")
                        .withTemperature(1.0f)
                        .build())
                .call()
                .content();
    }

}
