# Embedding Models: Ollama

Vector transformation (embeddings) with LLMs via Ollama.

## Description

Spring AI provides an `EmbeddingModel` abstraction for integrating with LLMs via several providers, including Mistral AI.

When using the _Spring AI Mistral AI Spring Boot Starter_, an `EmbeddingModel` object is autoconfigured for you to use Mistral AI.

```java
@RestController
class EmbeddingController {
    private final EmbeddingModel embeddingModel;

    EmbeddingController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/embed")
    String embed(@RequestParam(defaultValue = "And Gandalf yelled: 'You shall not pass!'") String message) {
        var embeddings = embeddingModel.embed(message);
        return "Size of the embedding vector: " + embeddings.size();
    }
}
```

## Running the application

The application relies on the Mistral AI API for providing LLMs.

### When using Mistral AI

First, make sure you have a [Mistral AI account](https://console.mistral.ai).
Then, define an environment variable with the Mistral AI API Key associated to your Mistral AI account as the value.

```shell
export SPRING_AI_MISTRALAI_API_KEY=<INSERT KEY HERE>
```

Finally, run the Spring Boot application.

```shell
./gradlew bootRun
```

## Calling the application

You can now call the application that will use Mistral AI and _mistral-embed_ to generate a vector representation (embeddings) of a default text.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/embed
```

Try passing your custom prompt and check the result.

```shell
http :8080/embed message=="The capital of Italy is Rome"
```

The next request is configured with Mistral AI-specific customizations.

```shell
http :8080/embed/mistral-ai-options message=="The capital of Italy is Rome"
```
