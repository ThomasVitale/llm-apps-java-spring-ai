package com.thomasvitale.ai.spring;

import org.springframework.ai.mcp.client.McpSyncClient;
import org.springframework.ai.mcp.spring.McpFunctionCallback;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

public final class McpFunctionCallbackResolver {

    public static FunctionCallback[] resolve(McpSyncClient... mcpClients) {
        Assert.notNull(mcpClients, "mcpClients cannot be null");
        return resolve(Arrays.asList(mcpClients));
    }

    public static FunctionCallback[] resolve(List<McpSyncClient> mcpClients) {
        Assert.notNull(mcpClients, "mcpClients cannot be null");
        Assert.noNullElements(mcpClients, "mcpClients cannot contain null elements");

        return mcpClients.stream()
                .flatMap(mcpClient -> mcpClient.listTools().tools().stream()
                        .map(tool -> (FunctionCallback) new McpFunctionCallback(mcpClient, tool)))
                .toArray(FunctionCallback[]::new);
    }

}
