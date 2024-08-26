package com.thomasvitale.ai.spring;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class TranscriptionController {

    private final OpenAiAudioTranscriptionModel transcriptionModel;

    TranscriptionController(OpenAiAudioTranscriptionModel transcriptionModel) {
        this.transcriptionModel = transcriptionModel;
    }

    @GetMapping("/transcription")
    String speech(@Value("classpath:speech1.mp3") Resource audioFile) {
        return transcriptionModel.call(new AudioTranscriptionPrompt(audioFile)).getResult().getOutput();
    }

    @GetMapping("/transcription/openai-options")
    String speechWithOpenAiOptions(@Value("classpath:speech2.mp3") Resource audioFile) {
        var transcriptionResponse = transcriptionModel.call(new AudioTranscriptionPrompt(audioFile, OpenAiAudioTranscriptionOptions.builder()
                .withLanguage("en")
                .withPrompt("Ask not this, but ask that")
                .withTemperature(0f)
                .withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.VTT)
                .build()));
        return transcriptionResponse.getResult().getOutput();
    }

}
