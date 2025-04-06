package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "arconia.dev.services.ollama.enabled=true")
@Disabled
class ObservabilityModelsOllamaApplicationTests {

    @Test
    void contextLoads() {
    }

}
