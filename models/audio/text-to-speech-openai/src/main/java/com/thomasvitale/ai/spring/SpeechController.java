package com.thomasvitale.ai.spring;

import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SpeechController {

    private final TextToSpeechModel speechModel;

    SpeechController(TextToSpeechModel speechModel) {
        this.speechModel = speechModel;
    }

    @GetMapping("/speech")
    byte[] speech(String message) {
        return speechModel.call(new TextToSpeechPrompt(message)).getResult().getOutput();
    }

    @GetMapping("/speech/provider-options")
    byte[] speechProviderOptions(String message) {
        var speechResponse = speechModel.call(new TextToSpeechPrompt(message, OpenAiAudioSpeechOptions.builder()
                .model("tts-1")
                .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .speed(1.0)
                .build()));
        return speechResponse.getResult().getOutput();
    }

}
