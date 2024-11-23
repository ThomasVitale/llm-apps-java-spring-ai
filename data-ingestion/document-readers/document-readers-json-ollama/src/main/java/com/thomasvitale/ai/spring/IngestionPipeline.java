package com.thomasvitale.ai.spring;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonMetadataGenerator;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
class IngestionPipeline {

    private static final Logger logger = LoggerFactory.getLogger(IngestionPipeline.class);

    private final VectorStore vectorStore;

    @Value("classpath:documents/bikes-1.json")
    private Resource jsonFile1;

    @Value("classpath:documents/bikes-2.json")
    private Resource jsonFile2;

    @Value("classpath:documents/bikes-3.json")
    private Resource jsonFile3;

    IngestionPipeline(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    void run() {
        List<Document> documents = new ArrayList<>();

        logger.info("Loading JSON as Documents");
        var jsonReader1 = new JsonReader(jsonFile1);
        documents.addAll(jsonReader1.get());

        logger.info("Loading JSON key subset as Documents");
        var jsonReader2 = new JsonReader(
                jsonFile2,
                "name", "price", "shortDescription", "description");
        documents.addAll(jsonReader2.get());

        logger.info("Loading JSON key subset and metadata as Documents");
        var jsonReader3 = new JsonReader(
                jsonFile3,
                new BikeJsonMetadataGenerator(),
                "name", "price", "shortDescription", "description");
        documents.addAll(jsonReader3.get());

        logger.info("Creating and storing Embeddings from Documents");
        vectorStore.add(documents);
    }

    static class BikeJsonMetadataGenerator implements JsonMetadataGenerator {
        @Override
        public Map<String, Object> generate(Map<String, Object> jsonMap) {
            return Map.of("name", jsonMap.get("name"));
        }
    }

}
