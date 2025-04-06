package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "arconia.dev.services.ollama.enabled=true")
class DocumentTransformersSplittersOllamaApplicationTests {

    @Test
    void contextLoads() {
    }

}
