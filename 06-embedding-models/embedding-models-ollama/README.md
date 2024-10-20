# Embedding Models: Ollama

Vector transformation (embeddings) with LLMs via Ollama.

## Description

Spring AI provides an `EmbeddingModel` abstraction for integrating with LLMs via several providers, including Ollama.

When using the _Spring AI Ollama Spring Boot Starter_, an `EmbeddingModel` object is autoconfigured for you to use Ollama.

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

The application relies on Ollama for providing LLMs. You can either run Ollama locally on your laptop, or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.
Either way, Spring AI will take care of pulling the needed Ollama models if not already available in your instance.

### Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop.

Then, run the Spring Boot application.

```shell
./gradlew bootRun
```

### Ollama as a dev service with Testcontainers

The application relies on the native Testcontainers support in Spring Boot to spin up an Ollama service at startup time.

```shell
./gradlew bootTestRun
```

## Calling the application

You can now call the application that will use Ollama to generate a vector representation (embeddings) of a default text.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/embed
```

Try passing your custom prompt and check the result.

```shell
http :8080/embed message=="The capital of Italy is Rome"
```

The next request is configured with custom options.

```shell
http :8080/embed/generic-options message=="The capital of Italy is Rome"
```
