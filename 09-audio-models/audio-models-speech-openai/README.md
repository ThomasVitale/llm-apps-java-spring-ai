# Audio Models: OpenAI

Speech generation with LLMs via OpenAI.

## Description

Spring AI provides an `SpeechModel` abstraction for integrating with LLMs via several providers, including OpenAI.

When using the _Spring AI OpenAI Spring Boot Starter_, an `SpeechModel` object is autoconfigured for you to use OpenAI.

```java
@RestController
class SpeechController {
    private final SpeechModel speechModel;

    SpeechController(SpeechModel speechModel) {
        this.speechModel = speechModel;
    }

    @GetMapping("/speech")
    byte[] speech(@RequestParam(defaultValue = "They're taking the Hobbits to Isengard! To Isengard! To Isengard") String message) {
        return speechModel.call(new SpeechPrompt(message)).getResult().getOutput();
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

You can now call the application that will use OpenAI and _tts-1_ to generate an image based on a default prompt.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/speech --download --output speech1.mp3
```

Try passing your custom prompt and check the result.

```shell
http :8080/speech message=="Yellow Submarine" --download --output speech2.mp3
```

The next request is configured with Open AI-specific customizations.

```shell
http :8080/speech/openai-options message=="Here comes the sun" --download --output speech3.mp3
```
