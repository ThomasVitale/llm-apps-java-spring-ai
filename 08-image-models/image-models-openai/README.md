# Image Models: OpenAI

Image generation with LLMs via OpenAI.

## Description

Spring AI provides an `ImageModel` abstraction for integrating with LLMs via several providers, including OpenAI.

When using the _Spring AI OpenAI Spring Boot Starter_, an `ImageModel` object is autoconfigured for you to use OpenAI.

```java
@RestController
class ImageController {
    private final ImageModel imageModel;

    ImageController(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @GetMapping("/image")
    String image(@RequestParam(defaultValue = "Here comes the sun") String message) {
        return imageModel.call(new ImagePrompt(message)).getResult().getOutput().getUrl();
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

You can now call the application that will use OpenAI and _dall-e-2_ to generate an image based on a default prompt.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/image
```

Try passing your custom prompt and check the result.

```shell
http :8080/image message=="Yellow Submarine"
```

The next request is configured with Open AI-specific customizations.

```shell
http :8080/image/openai-options message=="Here comes the sun"
```
