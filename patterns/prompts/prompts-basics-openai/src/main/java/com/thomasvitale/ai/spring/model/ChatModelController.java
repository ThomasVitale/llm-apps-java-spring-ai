package com.thomasvitale.ai.spring.model;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/chat/simple")
    String chatText(@RequestBody String question) {
        return chatModel.call(question);
    }

    @PostMapping("/chat/prompt")
    String chatPrompt(@RequestBody String question) {
        return chatModel.call(new Prompt(question)).getResult().getOutput().getText();
    }

    @PostMapping("/chat/full")
    ChatResponse chatFullResponse(@RequestBody String question) {
        return chatModel.call(new Prompt(question));
    }

}
