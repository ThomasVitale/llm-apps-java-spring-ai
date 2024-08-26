package com.thomasvitale.ai.spring;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
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

    @Value("classpath:documents/story1.pdf")
    Resource pdfFile1;

    @Value("classpath:documents/story2.pdf")
    Resource pdfFile2;

    public DocumentEtlPipeline(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void run() {
        List<Document> documents = new ArrayList<>();

        logger.info("Loading PDF files as Documents");
        var pdfReader1 = new PagePdfDocumentReader(pdfFile1);
        documents.addAll(pdfReader1.get());

        logger.info("Loading PDF files as Documents after reformatting");
        var pdfReader2 = new PagePdfDocumentReader(pdfFile2, PdfDocumentReaderConfig.builder()
                .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                        .withNumberOfTopPagesToSkipBeforeDelete(0)
                        .withNumberOfBottomTextLinesToDelete(1)
                        .withNumberOfTopTextLinesToDelete(1)
                        .build())
                .withPagesPerDocument(1)
                .build());
        documents.addAll(pdfReader2.get());

        logger.info("Creating and storing Embeddings from Documents");
        vectorStore.add(documents);
    }

}
