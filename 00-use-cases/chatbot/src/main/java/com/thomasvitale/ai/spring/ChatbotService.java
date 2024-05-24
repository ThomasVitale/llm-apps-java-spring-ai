package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.memory.*;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.transformer.ChatServiceContext;
import org.springframework.ai.chat.service.ChatService;
import org.springframework.ai.chat.service.PromptTransformingChatService;
import org.springframework.ai.tokenizer.TokenCountEstimator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ChatbotService {

    private final ChatService chatService;

    ChatbotService(ChatModel chatModel, ChatMemory chatMemory, TokenCountEstimator tokenCountEstimator) {
        this.chatService = PromptTransformingChatService.builder(chatModel)
                .withRetrievers(List.of(new ChatMemoryRetriever(chatMemory)))
                .withContentPostProcessors(List.of(new LastMaxTokenSizeContentTransformer(tokenCountEstimator, 1000)))
                .withAugmentors(List.of(new SystemPromptChatMemoryAugmentor()))
                .withChatServiceListeners(List.of(new ChatMemoryChatServiceListener(chatMemory)))
                .build();
    }

    String chat(String message) {
        var prompt = new Prompt(new UserMessage(message));
        var chatServiceResponse = this.chatService.call(new ChatServiceContext(prompt));
        return chatServiceResponse.getChatResponse().getResult().getOutput().getContent();
    }

}
