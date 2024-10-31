# Embedding Models: OpenAI

Vector transformation (embeddings) with LLMs via OpenAI.

## Description

Spring AI provides an `EmbeddingModel` abstraction for integrating with LLMs via several providers, including OpenAI.

When using the _Spring AI OpenAI Spring Boot Starter_, an `EmbeddingModel` object is autoconfigured for you to use OpenAI.

```java
@Bean
CommandLineRunner embed(EmbeddingModel embeddingModel) {
    return _ -> {
        var embeddings = embeddingModel.embed("And Gandalf yelled: 'You shall not pass!'");
        System.out.println("Size of the embedding vector: " + embeddings.length);
    };
}
```

## OpenAI

The application relies on the OpenAI API for providing LLMs.

### Create an OpenAI account

Visit [https://platform.openai.com](platform.openai.com) and sign up for a new account.

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
