# Embedding Models: Ollama

Vector transformation (embeddings) with LLMs via Ollama.

## Description

Spring AI provides an `EmbeddingModel` abstraction for integrating with LLMs via several providers, including Ollama.

When using the _Spring AI Ollama Spring Boot Starter_, an `EmbeddingModel` object is autoconfigured for you to use Ollama.

```java
@Bean
CommandLineRunner embed(EmbeddingModel embeddingModel) {
    return _ -> {
        var embeddings = embeddingModel.embed("And Gandalf yelled: 'You shall not pass!'");
        System.out.println("Size of the embedding vector: " + embeddings.length);
    };
}
```

## Ollama

The application consumes models from an [Ollama](https://ollama.ai) inference server. You can either run Ollama locally on your laptop,
or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.
If you choose the first option, make sure you have Ollama installed and running on your laptop.
Either way, Spring AI will take care of pulling the needed Ollama models when the application starts,
if they are not available yet on your machine.

## Running the application

If you're using the native Ollama application, run the application as follows.

```shell
./gradlew bootRun
```

If you want to rely on the native Testcontainers support in Spring Boot to spin up an Ollama service at startup time,
run the application as follows.

```shell
./gradlew bootTestRun
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

The next request is configured with the provider's specific options.

```shell
http :8080/embed/provider-options query=="The capital of Italy is Rome" -b
```
