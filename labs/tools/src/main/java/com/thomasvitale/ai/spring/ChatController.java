package com.thomasvitale.ai.spring;

import com.thomasvitale.ai.spring.api.tools.mcp.McpToolCallbackResolver;
import com.thomasvitale.ai.spring.api.tools.method.MethodToolCallbackResolver;
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
    private final Tools tools;

    ChatController(ChatClient.Builder chatClientBuilder, McpSyncClient mcpClient, Tools tools) {
        this.chatClient = chatClientBuilder.build();
        this.mcpClient = mcpClient;
        this.tools = tools;
    }

    @GetMapping("/chat/method/void")
    String chatMethodVoid() {
        return chatClient.prompt()
                .user("Welcome the user to the library")
                .functions(MethodToolCallbackResolver.builder()
                        .target(tools)
                        .build()
                        .getToolCallbacks())
                .call()
                .content();
    }

    @GetMapping("/chat/method/single")
    String chatMethodSingle(String authorName) {
        var userPromptTemplate = "What books written by {author} are available in the library?";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("author", authorName)
                )
                .functions(MethodToolCallbackResolver.builder()
                        .target(tools)
                        .build()
                        .getToolCallbacks())
                .call()
                .content();
    }

    @GetMapping("/chat/method/list")
    String chatMethodList(String authorName1, String authorName2) {
        var userPromptTemplate = "What books written by {authorName1} and {authorName2} are available in the library?";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("authorName1", authorName1)
                        .param("authorName2", authorName2)
                )
                .functions(MethodToolCallbackResolver.builder()
                        .target(tools)
                        .build()
                        .getToolCallbacks())
                .call()
                .content();
    }

    @GetMapping("/chat/mcp")
    String chatMcp(String question) {
        return chatClient.prompt()
                .user(question)
                .functions(McpToolCallbackResolver.builder()
                        .mcpClients(mcpClient)
                        .build()
                        .getToolCallbacks())
                .call()
                .content();
    }

}
