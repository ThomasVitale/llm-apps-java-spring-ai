# Embedding Models: Sentence Transformers

Vector transformation (embeddings) with LLMs via ONNX Sentence Transformers.

## Description

Spring AI provides an `EmbeddingModel` abstraction for integrating with LLMs via several providers, including ONNX Sentence Transformers.

When using the _Spring AI Transformers Spring Boot Starter_, an `EmbeddingModel` object is autoconfigured for you to ONNX Sentence Transformers.

```java
@Bean
CommandLineRunner embed(EmbeddingModel embeddingModel) {
    return _ -> {
        var embeddings = embeddingModel.embed("And Gandalf yelled: 'You shall not pass!'");
        System.out.println("Size of the embedding vector: " + embeddings.length);
    };
}
```

## ONNX Sentence Transformers

The application relies on the ONNX Sentence Transformers for providing LLMs.
ONNX provides an in-process runtime to run model inference directly in Java.
Spring AI will take care of pulling the needed models when the application starts,
if they are not available yet on your machine.

## Running the application

Run the application.

```shell
./gradlew bootRun
```

## Calling the application

> [!NOTE]
> These examples use the [httpie](https://httpie.io) CLI to send HTTP requests.

Call the application that will use an embedding model to generate embeddings for your query.

```shell
http :8080/embed query=="The capital of Italy is Rome"
```

The next request is configured with generic portable options.

```shell
http :8080/embed/generic-options query=="The capital of Italy is Rome" -b
```
