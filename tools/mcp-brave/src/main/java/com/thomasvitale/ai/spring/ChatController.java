package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.client.McpSyncClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private final ChatClient chatClient;
    private final McpSyncClient mcpClient;

    ChatController(ChatClient.Builder chatClientBuilder, McpSyncClient mcpClient) {
        this.chatClient = chatClientBuilder.build();
        this.mcpClient = mcpClient;
    }

    @GetMapping("/chat/mcp")
    String chat(String question) {
        return chatClient.prompt()
                .user(question)
                .functions(McpFunctionCallbackResolver.resolve(mcpClient))
                .call()
                .content();
    }

}
