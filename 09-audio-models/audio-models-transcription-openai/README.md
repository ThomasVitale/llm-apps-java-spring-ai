# Audio Models: OpenAI

Speech transcription with LLMs via OpenAI.

## Description

Spring AI provides a `TranscriptionModel` abstraction for integrating with LLMs via several providers, including OpenAI.

When using the _Spring AI OpenAI Spring Boot Starter_, an `OpenAiAudioTranscriptionModel` object is autoconfigured for you to use OpenAI.

```java
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
}
```

## Running the application

The application relies on an OpenAI API for providing LLMs.

### When using OpenAI

First, make sure you have an [OpenAI account](https://platform.openai.com/signup).
Then, define an environment variable with the OpenAI API Key associated to your OpenAI account as the value.

```shell
export SPRING_AI_OPENAI_API_KEY=<INSERT KEY HERE>
```

Finally, run the Spring Boot application.

```shell
./gradlew bootRun
```

## Calling the application

You can now call the application that will use OpenAI and _whisper-1_ to transcribe speech from an audio file.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/transcription
```

The next request is configured with Open AI-specific customizations.

```shell
http :8080/transcription/openai-options
```
