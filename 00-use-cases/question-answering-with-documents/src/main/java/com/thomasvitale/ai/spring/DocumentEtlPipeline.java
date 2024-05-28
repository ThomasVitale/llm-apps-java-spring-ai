package com.thomasvitale.ai.spring;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentEtlPipeline {

    private static final Logger logger = LoggerFactory.getLogger(DocumentEtlPipeline.class);
    private final VectorStore vectorStore;

    @Value("classpath:documents/story1.md")
    Resource textFile1;

    @Value("classpath:documents/story2.txt")
    Resource textFile2;

    public DocumentEtlPipeline(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void run() {
        List<Document> documents = new ArrayList<>();

        logger.info("Loading .md files as Documents");
        var textReader1 = new TextReader(textFile1);
        textReader1.getCustomMetadata().put("location", "North Pole");
        textReader1.setCharset(Charset.defaultCharset());
        documents.addAll(textReader1.get());

        logger.info("Loading .txt files as Documents");
        var textReader2 = new TextReader(textFile2);
        textReader2.getCustomMetadata().put("location", "Italy");
        textReader2.setCharset(Charset.defaultCharset());
        documents.addAll(textReader2.get());

        logger.info("Creating and storing Embeddings from Documents");
        vectorStore.add(new TokenTextSplitter().split(documents));
    }

}
