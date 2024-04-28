# Embedding Models: Sentence Transformers

Vector transformation (embeddings) with LLMs via ONNX Sentence Transformers.

## Description

Spring AI provides an `EmbeddingClient` abstraction for integrating with LLMs via several providers, including ONNX Sentence Transformers.

When using the _Spring AI Transformers Spring Boot Starter_, an `EmbeddingClient` object is autoconfigured for you to use ONNX Sentence Transformers.

```java
@RestController
class EmbeddingController {
    private final EmbeddingClient embeddingClient;

    EmbeddingController(EmbeddingClient embeddingClient) {
        this.embeddingClient = embeddingClient;
    }

    @GetMapping("/embed")
    String embed(@RequestParam(defaultValue = "And Gandalf yelled: 'You shall not pass!'") String message) {
        var embeddings = embeddingClient.embed(message);
        return "Size of the embedding vector: " + embeddings.size();
    }
}
```

## Running the application

Run the Spring Boot application.

```shell
./gradlew bootRun
```

## Calling the application

You can now call the application that will use the _all-MiniLM-L6-v2_ model from HuggingFace to generate a vector representation (embeddings) of a default text.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/embed
```

Try passing your custom prompt and check the result.

```shell
http :8080/embed message=="The capital of Italy is Rome"
```
