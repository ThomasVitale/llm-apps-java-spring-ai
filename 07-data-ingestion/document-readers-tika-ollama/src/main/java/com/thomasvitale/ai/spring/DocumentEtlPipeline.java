package com.thomasvitale.ai.spring;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentEtlPipeline {

    private static final Logger logger = LoggerFactory.getLogger(DocumentEtlPipeline.class);
    private final VectorStore vectorStore;

    @Value("classpath:documents/story1.md")
    Resource file1;

    @Value("classpath:documents/story2.pdf")
    Resource file2;

    public DocumentEtlPipeline(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void run() {
        List<Document> documents = new ArrayList<>();

        logger.info("Loading files as Documents");
        var tikaReader1 = new TikaDocumentReader(file1);
        documents.addAll(tikaReader1.get());

        var tikaReader2 = new TikaDocumentReader(file2);
        documents.addAll(tikaReader2.get());

        logger.info("Creating and storing Embeddings from Documents");
        vectorStore.add(documents);
    }

}
