package com.thomasvitale.ai.spring;

import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class SemanticSearchController {

    private final VectorStore vectorStore;

    SemanticSearchController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostMapping("/semantic-search")
    List<InstrumentNote> semanticSearch(@RequestBody String query) {
        return vectorStore.similaritySearch(SearchRequest.query(query).withTopK(3))
                .stream()
                .map(document -> new InstrumentNote(document.getContent()))
                .toList();
    }

}
