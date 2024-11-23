# Audio Models (Text-to-speech): OpenAI

Speech generation with LLMs via OpenAI.

## Description

Spring AI provides an `SpeechModel` abstraction for integrating with LLMs via several providers, including OpenAI.

When using the _Spring AI OpenAI Spring Boot Starter_, an `SpeechModel` object is autoconfigured for you to use OpenAI.

```java
@Bean
CommandLineRunner chat(SpeechModel speechModel) {
    return _ -> {
        var response = speechModel.call(new SpeechPrompt(message)).getResult().getOutput();
        System.out.println(response);
    };
}
```

## OpenAI

The application consumes models from the [OpenAI](https://openai.com) platform.

### Create an account

Visit [platform.openai.com](https://platform.openai.com) and sign up for a new account.

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

Call the application that will use an audio model to generate speech.

```shell
http :8080/speech message=="They're taking the Hobbits to Isengard, to Isengard, to Isengard" --download --output speech1.mp3
```

The next request is configured with provider-specific customizations.

```shell
http :8080/speech/provider-options message=="Here comes the sun. Du du du du." --download --output speech2.mp3
```
