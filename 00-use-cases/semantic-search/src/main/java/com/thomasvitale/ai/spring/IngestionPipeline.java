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
public class IngestionPipeline {

    private static final Logger logger = LoggerFactory.getLogger(IngestionPipeline.class);
    private final VectorStore vectorStore;

    public IngestionPipeline(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void run() {
        var instrumentNotes = List.of(
                new InstrumentNote("The haunting sound of the cello evokes a deep sense of melancholy and introspection."),
                new InstrumentNote("The lively strumming of the acoustic guitar brings forth feelings of joy and carefree summer days."),
                new InstrumentNote("The ethereal notes of the harp create a serene and calming atmosphere, reminiscent of a peaceful dream."),
                new InstrumentNote("The majestic blast of the trumpet instills a sense of triumph and victory."),
                new InstrumentNote("The smooth, mellow tones of the saxophone convey a sense of romance and late-night nostalgia."),
                new InstrumentNote("The rhythmic beats of the drum set evoke a primal energy and raw excitement."),
                new InstrumentNote("The bright, tinkling notes of the xylophone spark a playful and whimsical feeling."),
                new InstrumentNote("The rich harmonies of the piano elicit a profound sense of elegance and emotional depth."),
                new InstrumentNote("The airy sound of the flute carries a feeling of lightness and carefree freedom."),
                new InstrumentNote("The resonant strings of the violin draw out a sense of passionate longing and dramatic intensity.")
        );

        logger.info("Creating InstrumentNotes as Documents");
        List<Document> documents = instrumentNotes.stream()
                .map(note -> new Document(note.content()))
                .toList();

        logger.info("Creating and storing Embeddings from Documents");
        vectorStore.add(documents);
    }

}
