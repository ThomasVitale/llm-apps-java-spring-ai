package com.thomasvitale.ai.spring;

import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechClient;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SpeechController {

    private final SpeechClient speechClient;

    SpeechController(SpeechClient speechClient) {
        this.speechClient = speechClient;
    }

    @GetMapping("/speech")
    byte[] speech(@RequestParam(defaultValue = "They're taking the Hobbits to Isengard! To Isengard! To Isengard") String message) {
        return speechClient.call(new SpeechPrompt(message)).getResult().getOutput();
    }

    @GetMapping("/speech/openai-options")
    byte[] speechWithOpenAiOptions(@RequestParam(defaultValue = "They're taking the Hobbits to Isengard! To Isengard! To Isengard") String message) {
        var speechResponse = speechClient.call(new SpeechPrompt(message, OpenAiAudioSpeechOptions.builder()
                .withModel("tts-1")
                .withVoice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .withSpeed(1.0f)
                .build()));
        return speechResponse.getResult().getOutput();
    }

}
