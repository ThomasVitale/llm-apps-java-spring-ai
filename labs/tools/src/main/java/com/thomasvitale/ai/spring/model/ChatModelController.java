package com.thomasvitale.ai.spring.model;

import com.thomasvitale.ai.spring.Tools;
import com.thomasvitale.ai.spring.api.tools.mcp.McpToolCallbackResolver;
import com.thomasvitale.ai.spring.api.tools.method.MethodToolCallbackResolver;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.mcp.client.McpSyncClient;
import org.springframework.ai.model.function.FunctionCallingOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Chat examples using the low-level ChatModel API.
 */
@RestController
@RequestMapping("/model")
class ChatModelController {

    private final ChatModel chatModel;
    private final McpSyncClient mcpClient;
    private final Tools tools;

    ChatModelController(ChatModel chatModel, McpSyncClient mcpClient, Tools tools) {
        this.chatModel = chatModel;
        this.mcpClient = mcpClient;
        this.tools = tools;
    }

    @GetMapping("/chat/method")
    String chatMethod(String authorName) {
        var userPromptTemplate = new PromptTemplate("""
                What books written by {author} are available in the library?
                """);
        Map<String,Object> model = Map.of("author", authorName);
        var prompt = userPromptTemplate.create(model, FunctionCallingOptions.builder()
                .functionCallbacks(MethodToolCallbackResolver.builder()
                        .target(tools)
                        .build()
                        .getToolCallbacks())
                .build());

        var chatResponse = chatModel.call(prompt);
        return chatResponse.getResult().getOutput().getText();
    }

    @GetMapping("/chat/mcp")
    String chatMcp(String question) {
        var prompt = new Prompt(question, FunctionCallingOptions.builder()
                .functionCallbacks(McpToolCallbackResolver.builder()
                        .mcpClients(mcpClient)
                        .build()
                        .getToolCallbacks())
                .build());

        var chatResponse = chatModel.call(prompt);
        return chatResponse.getResult().getOutput().getText();
    }

}
