package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestDocumentReadersPdfOllamaApplication.class)
@Disabled // Only run locally for now
class DocumentReadersPdfOllamaApplicationTests {

    @Test
    void contextLoads() {
    }

}
