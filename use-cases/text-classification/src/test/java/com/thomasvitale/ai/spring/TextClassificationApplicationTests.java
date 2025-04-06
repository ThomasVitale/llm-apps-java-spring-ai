package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "arconia.dev.services.ollama.enabled=true")
class TextClassificationApplicationTests {

    @Test
    void contextLoads() {
    }

}
