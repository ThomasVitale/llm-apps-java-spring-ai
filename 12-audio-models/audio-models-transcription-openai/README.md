# Audio Models: OpenAI

Speech transcription with LLMs via OpenAI.

## Description

Spring AI provides a `TranscriptionModel` abstraction for integrating with LLMs via several providers, including OpenAI.

When using the _Spring AI OpenAI Spring Boot Starter_, an `OpenAiAudioTranscriptionModel` object is autoconfigured for you to use OpenAI.

```java
@Bean
CommandLineRunner chat(OpenAiAudioTranscriptionModel transcriptionModel) {
    return _ -> {
        var response = transcriptionModel.call(new AudioTranscriptionPrompt(audioFile)).getResult().getOutput();
        System.out.println(response);
    };
}
```

## OpenAI

The application relies on the OpenAI API for providing LLMs.

### Create an OpenAI account

Visit [https://platform.openai.com](platform.openai.com) and sign up for a new account.

### Configure API Key

In the OpenAI console, navigate to _Dashboard > API Keys_ and generate a new API key.
Copy and securely store your API key on your machine as an environment variable.
The application will use it to access the OpenAI API.

```shell
export OPENAI_API_KEY=<YOUR-API-KEY>
```

## Running the application

Run the application.

```shell
./gradlew bootRun
```

## Calling the application

> [!NOTE]
> These examples use the [httpie](https://httpie.io) CLI to send HTTP requests.

Call the application that will use an audio model to transcribe speech.

```shell
http :8080/transcription
```

The next request is configured with provider-specific customizations.

```shell
http :8080/transcription/provider-options
```
