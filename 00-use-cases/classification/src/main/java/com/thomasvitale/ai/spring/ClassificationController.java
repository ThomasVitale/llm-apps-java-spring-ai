package com.thomasvitale.ai.spring;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ClassificationController {

    private final ClassificationService classificationService;

    ClassificationController(ClassificationService classificationService) {
        this.classificationService = classificationService;
    }

    @PostMapping("/classify/simple")
    String classifySimple(@RequestBody String input) {
        return classificationService.classifySimple(input);
    }

    @PostMapping("/classify/structured")
    ClassificationType classifyStructured(@RequestBody String input) {
        return classificationService.classifyStructured(input);
    }

    @PostMapping("/classify/few-shots-prompt")
    ClassificationType classifyFewShotsPrompt(@RequestBody String input) {
        return classificationService.classifyFewShotsPrompt(input);
    }

    @PostMapping("/classify/few-shots-history")
    ClassificationType classifyFewShotsHistory(@RequestBody String input) {
        return classificationService.classifyFewShotsHistory(input);
    }

}
