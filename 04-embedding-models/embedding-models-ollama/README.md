# Embedding Models: Ollama

Vector transformation (embeddings) with LLMs via Ollama.

## Description

Spring AI provides an `EmbeddingClient` abstraction for integrating with LLMs via several providers, including Ollama.

When using the _Spring AI Ollama Spring Boot Starter_, an `EmbeddingClient` object is autoconfigured for you to use Ollama.
By default, the _llama2_ model is used.

```java
@RestController
class EmbeddingController {
    private final EmbeddingClient embeddingClient;

    EmbeddingController(EmbeddingClient embeddingClient) {
        this.embeddingClient = embeddingClient;
    }

    @GetMapping("/ai/embed")
    String embed(@RequestParam(defaultValue = "And Gandalf yelled: 'You shall not pass!'") String message) {
        var embeddings = embeddingClient.embed(message);
        return "Size of the embedding vector: " + embeddings.size();
    }
}
```

## Running the application

The application relies on Ollama for providing LLMs. You can either run Ollama locally on your laptop (macOS or Linux), or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.

### Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop (macOS or Linux).
Then, use Ollama to run the _llama2_ large language model.

```shell
ollama run llama2
```

Finally, run the Spring Boot application.

```shell
./gradlew bootRun
```

### Ollama as a dev service with Testcontainers

The application relies on the native Testcontainers support in Spring Boot to spin up an Ollama service with a _llama2_ model at startup time.

```shell
./gradlew bootTestRun
```

## Calling the application

You can now call the application that will use Ollama and llama2 to generate a vector representation (embeddings) of a default text.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/ai/embed
```

Try passing your custom prompt and check the result.

```shell
http :8080/ai/embed message=="The capital of Italy is Rome"
```
