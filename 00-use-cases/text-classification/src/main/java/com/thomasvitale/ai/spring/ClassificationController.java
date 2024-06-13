package com.thomasvitale.ai.spring;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ClassificationController {

    private final TextClassifier textClassifier;

    ClassificationController(TextClassifier textClassifier) {
        this.textClassifier = textClassifier;
    }

    @PostMapping("/classify/class-names")
    String classifyClassNames(@RequestBody String text) {
        return textClassifier.classifyClassNames(text);
    }

    @PostMapping("/classify/class-descriptions")
    String classifyClassDescriptions(@RequestBody String input) {
        return textClassifier.classifyClassDescriptions(input);
    }

    @PostMapping("/classify/few-shots-prompt")
    String classifyFewShotsPrompt(@RequestBody String input) {
        return textClassifier.classifyFewShotsPrompt(input);
    }

    @PostMapping("/classify/few-shots-history")
    String classifyFewShotsHistory(@RequestBody String input) {
        return textClassifier.classifyFewShotsHistory(input);
    }

    @PostMapping("/classify/structured-output")
    ClassificationType classifyStructured(@RequestBody String input) {
        return textClassifier.classifyStructured(input);
    }

}
