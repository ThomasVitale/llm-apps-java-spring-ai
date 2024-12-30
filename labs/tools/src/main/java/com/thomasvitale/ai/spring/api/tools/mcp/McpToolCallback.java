package com.thomasvitale.ai.spring.api.tools.mcp;

import com.thomasvitale.ai.spring.api.tools.ToolCallback;
import org.springframework.ai.mcp.client.McpSyncClient;
import org.springframework.ai.mcp.spec.McpSchema;
import org.springframework.ai.mcp.spring.McpFunctionCallback;

public class McpToolCallback extends McpFunctionCallback implements ToolCallback {

    public McpToolCallback(McpSyncClient clientSession, McpSchema.Tool tool) {
        super(clientSession, tool);
    }

}
