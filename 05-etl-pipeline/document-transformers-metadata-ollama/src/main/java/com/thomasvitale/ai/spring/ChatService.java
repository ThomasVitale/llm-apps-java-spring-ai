package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class ChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    ChatService(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    String chatWithDocument(String message) {
        var systemPromptTemplate = new SystemPromptTemplate("""
                You are a helpful assistant, conversing with a user about the subjects contained in a set of documents.
                Use the information from the DOCUMENTS section to provide accurate answers. If unsure or if the answer
                isn't found in the DOCUMENTS section, simply state that you don't know the answer and do not mention
                the DOCUMENTS section.
                
                DOCUMENTS:
                {documents}
                """);

        List<Document> similarDocuments = vectorStore.similaritySearch(SearchRequest.query(message).withTopK(5));
        String content = similarDocuments.stream().map(Document::getContent).collect(Collectors.joining(System.lineSeparator()));

        Map<String,Object> model = Map.of("documents", content);
        var systemMessage = systemPromptTemplate.createMessage(model);

        var userMessage = new UserMessage(message);

        return chatClient.call(systemMessage, userMessage);
    }

}
