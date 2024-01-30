package com.thomasvitale.ai.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.ai.reader.pdf.PagePdfDocumentReader.*;

@Service
class ChatService {

    private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    private final ChatClient chatClient;
    private final SimpleVectorStore vectorStore;

    ChatService(ChatClient chatClient, SimpleVectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    AssistantMessage chatWithDocument(String message) {
        var systemPromptTemplate = new SystemPromptTemplate("""
                Answer questions given the context information below (DOCUMENTS section) and no prior knowledge,
                but act as if you knew this information innately. If the answer is not found in the DOCUMENTS section,
                simply state that you don't know the answer. In the answer, include the source file name from which
                the context information is extracted from.
                                
                DOCUMENTS:
                {documents}
                """);

        List<Document> similarDocuments = vectorStore.similaritySearch(SearchRequest.query(message).withTopK(2));
        logDocumentMetadata(similarDocuments);
        String documents = similarDocuments.stream().map(Document::getContent).collect(Collectors.joining(System.lineSeparator()));

        Map<String,Object> model = Map.of("documents", documents);
        var systemMessage = systemPromptTemplate.createMessage(model);

        var userMessage = new UserMessage(message);
        var prompt = new Prompt(List.of(systemMessage, userMessage));

        var chatResponse = chatClient.call(prompt);
        return chatResponse.getResult().getOutput();
    }

    private void logDocumentMetadata(List<Document> documents) {
        log.info("Similar documents retrieved to answer your question:");
        documents.forEach(document -> {
            var metadata = Map.of(
                    "fileName", document.getMetadata().get(METADATA_FILE_NAME),
                    "startPageNumber", document.getMetadata().get(METADATA_START_PAGE_NUMBER),
                    "endPageNumber", Objects.requireNonNullElse(document.getMetadata().get(METADATA_END_PAGE_NUMBER), "-")
            );
            log.info("Metadata: " + metadata);
        });
    }

}
