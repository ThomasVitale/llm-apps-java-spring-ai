package com.thomasvitale.ai.spring;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentInitializer {

    private static final Logger log = LoggerFactory.getLogger(DocumentInitializer.class);
    private final SimpleVectorStore vectorStore;

    @Value("classpath:documents/story1.pdf")
    Resource pdfFile1;

    @Value("classpath:documents/story2.pdf")
    Resource pdfFile2;

    public DocumentInitializer(SimpleVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void run() {
        List<Document> documents = new ArrayList<>();

        log.info("Loading PDF files as Documents");
        var pdfReader1 = new PagePdfDocumentReader(pdfFile1);
        documents.addAll(pdfReader1.get());

        log.info("Loading PDF files as Documents after reformatting");
        var pdfReader2 = new PagePdfDocumentReader(pdfFile2, PdfDocumentReaderConfig.builder()
                .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                        .withNumberOfTopPagesToSkipBeforeDelete(0)
                        .withNumberOfBottomTextLinesToDelete(1)
                        .withNumberOfTopTextLinesToDelete(1)
                        .build())
                .withPagesPerDocument(1)
                .build());
        documents.addAll(pdfReader2.get());

        log.info("Creating and storing Embeddings from Documents");
        vectorStore.add(documents);
    }

}
