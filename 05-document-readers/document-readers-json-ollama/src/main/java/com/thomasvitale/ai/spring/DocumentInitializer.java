package com.thomasvitale.ai.spring;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonMetadataGenerator;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DocumentInitializer {

    private static final Logger log = LoggerFactory.getLogger(DocumentInitializer.class);
    private final SimpleVectorStore simpleVectorStore;

    @Value("classpath:documents/bikes-1.json")
    Resource jsonFile1;

    @Value("classpath:documents/bikes-2.json")
    Resource jsonFile2;

    @Value("classpath:documents/bikes-3.json")
    Resource jsonFile3;

    public DocumentInitializer(SimpleVectorStore simpleVectorStore) {
        this.simpleVectorStore = simpleVectorStore;
    }

    @PostConstruct
    public void run() {
        List<Document> documents = new ArrayList<>();

        log.info("Loading JSON as Documents");
        var jsonReader1 = new JsonReader(jsonFile1);
        documents.addAll(jsonReader1.get());

        log.info("Loading JSON key subset as Documents");
        var jsonReader2 = new JsonReader(
                jsonFile2,
                "name", "price", "shortDescription", "description");
        documents.addAll(jsonReader2.get());

        log.info("Loading JSON key subset and metadata as Documents");
        var jsonReader3 = new JsonReader(
                jsonFile3,
                new BikeJsonMetadataGenerator(),
                "name", "price", "shortDescription", "description");
        documents.addAll(jsonReader3.get());

        log.info("Creating and storing Embeddings from Documents");
        simpleVectorStore.add(documents);
    }

    static class BikeJsonMetadataGenerator implements JsonMetadataGenerator {
        @Override
        public Map<String, Object> generate(Map<String, Object> jsonMap) {
            return Map.of("name", jsonMap.get("name"));
        }
    }

}
