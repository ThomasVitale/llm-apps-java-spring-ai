package com.thomasvitale.ai.spring;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
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

    @Value("classpath:documents/story2.md")
    Resource file2;

    public DocumentEtlPipeline(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void run() {
        List<Document> documents = new ArrayList<>();

        logger.info("Loading .md files as Documents");
        var markdownReader1 = new MarkdownDocumentReader(file1, MarkdownDocumentReaderConfig.builder()
                .withAdditionalMetadata("location", "North Pole")
                .build());
        documents.addAll(markdownReader1.get());
        var markdownReader2 = new MarkdownDocumentReader(file2, MarkdownDocumentReaderConfig.builder()
                .withAdditionalMetadata("location", "Italy")
                .build());
        documents.addAll(markdownReader2.get());

        logger.info("Creating and storing Embeddings from Documents");
        vectorStore.add(new TokenTextSplitter().split(documents));
    }

}
