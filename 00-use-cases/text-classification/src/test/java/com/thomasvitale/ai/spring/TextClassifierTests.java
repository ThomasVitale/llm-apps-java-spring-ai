package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
public class TextClassifierTests {

    @Autowired
    TextClassifier textClassifier;

    @Test
    void classifyBusinessNews() {
        var classificationType = textClassifier.classifyStructured(
                "The stocks for ACME Inc. have been skyrocketing.");
        assertThat(classificationType).isEqualTo(ClassificationType.BUSINESS);
    }

    @Test
    void classifyTechnologyNews() {
        var classificationType = textClassifier.classifyStructured(
                "Apple Vision Pro supports spatial computing for enhanced personal experiences and productivity.");
        assertThat(classificationType).isEqualTo(ClassificationType.TECHNOLOGY);
    }

}
