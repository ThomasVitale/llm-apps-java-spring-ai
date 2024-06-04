package com.thomasvitale.ai.spring;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class StructuredDataExtractionController {

    private final StructuredDataExtractionService structuredDataExtractionService;

    StructuredDataExtractionController(StructuredDataExtractionService structuredDataExtractionService) {
        this.structuredDataExtractionService = structuredDataExtractionService;
    }

    @PostMapping("/extract")
    PatientJournal extract(@RequestBody String input) {
        return structuredDataExtractionService.extract(input);
    }

}
