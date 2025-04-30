package com.thomasvitale.ai.spring;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Chat examples using the high-level ChatClient API.
 */
@RestController
class ChatController {

    private final ChatClient chatClient;
    private final List<McpSyncClient> mcpClients;

    ChatController(ChatClient.Builder chatClientBuilder, List<McpSyncClient> mcpClients) {
        this.chatClient = chatClientBuilder.build();
        this.mcpClients = mcpClients;
    }

    @GetMapping("/chat/mcp")
    String chat(String question) {
        return chatClient.prompt()
                .user(question)
                .toolCallbacks(new SyncMcpToolCallbackProvider(mcpClients).getToolCallbacks())
                .call()
                .content();
    }

}
