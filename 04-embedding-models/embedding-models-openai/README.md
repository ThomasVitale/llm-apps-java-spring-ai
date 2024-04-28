# Embedding Models: OpenAI

Vector transformation (embeddings) with LLMs via OpenAI.

## Description

Spring AI provides an `EmbeddingClient` abstraction for integrating with LLMs via several providers, including OpenAI.

When using the _Spring AI OpenAI Spring Boot Starter_, an `EmbeddingClient` object is autoconfigured for you to use OpenAI.

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

The application relies on an OpenAI API for providing LLMs.

### When using OpenAI

First, make sure you have an OpenAI account.
Then, define an environment variable with the OpenAI API Key associated to your OpenAI account as the value.

```shell
export SPRING_AI_OPENAI_API_KEY=<INSERT KEY HERE>
```

Finally, run the Spring Boot application.

```shell
./gradlew bootRun
```

## Calling the application

You can now call the application that will use OpenAI and _text-embedding-ada-002_ to generate a vector representation (embeddings) of a default text.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/embed
```

Try passing your custom prompt and check the result.

```shell
http :8080/embed message=="The capital of Italy is Rome"
```

The next request is configured with OpenAI-specific customizations.

```shell
http :8080/embed/openai-options message=="The capital of Italy is Rome"
```
