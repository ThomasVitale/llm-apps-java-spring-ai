package com.thomasvitale.ai.spring.model;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Chat examples using the low-level ChatModel API.
 */
@RestController
@RequestMapping("/model")
class ChatModelController {

    private final ChatModel chatModel;
    private final List<McpSyncClient> mcpClients;

    ChatModelController(ChatModel chatModel, List<McpSyncClient> mcpClients) {
        this.chatModel = chatModel;
        this.mcpClients = mcpClients;
    }

    @GetMapping("/chat/mcp")
    String chat(String question) {
        var prompt = new Prompt(question, ToolCallingChatOptions.builder()
                .toolCallbacks(new SyncMcpToolCallbackProvider(mcpClients).getToolCallbacks())
                .build());

        var chatResponse = chatModel.call(prompt);
        return chatResponse.getResult().getOutput().getText();
    }

}
