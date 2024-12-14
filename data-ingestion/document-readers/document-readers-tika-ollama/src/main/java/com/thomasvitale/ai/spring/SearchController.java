package com.thomasvitale.ai.spring;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class SearchController {

    private final VectorStore vectorStore;

    SearchController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostMapping("/search/simple")
    List<Document> searchSimilarDocuments(@RequestBody String query) {
        var documents = vectorStore.similaritySearch(query);
        return documents.stream()
                .map(document -> document.mutate().build())
                .toList();
    }

}
