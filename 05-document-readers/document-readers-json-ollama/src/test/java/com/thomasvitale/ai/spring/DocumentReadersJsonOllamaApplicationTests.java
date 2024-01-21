package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestDocumentReadersJsonOllamaApplication.class)
class DocumentReadersJsonOllamaApplicationTests {

    @Test
    void contextLoads() {
    }

}
