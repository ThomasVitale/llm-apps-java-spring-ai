package com.thomasvitale.ai.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.client.McpClient;
import org.springframework.ai.mcp.client.McpSyncClient;
import org.springframework.ai.mcp.client.transport.ServerParameters;
import org.springframework.ai.mcp.client.transport.StdioClientTransport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpBraveApplication {

    private static final Logger logger = LoggerFactory.getLogger(McpBraveApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(McpBraveApplication.class, args);
    }

    @Bean
    public McpSyncClient mcpClient() {
        var serverParameters = ServerParameters.builder("npx")
                .args("-y", "@modelcontextprotocol/server-brave-search")
                .addEnvVar("BRAVE_API_KEY", System.getenv("BRAVE_API_KEY"))
                .build();

        var mcpClient = McpClient.sync(new StdioClientTransport(serverParameters)).build();

        var initializeResult = mcpClient.initialize();
        logger.info("MCP Initialized: {}", initializeResult);

        return mcpClient;
    }

}
