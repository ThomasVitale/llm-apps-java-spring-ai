# Semantic Search

Semantic search with LLMs via Ollama and PGVector.

## Ollama

The application consumes models from an [Ollama](https://ollama.ai) inference server. You can either run Ollama locally on your laptop,
or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.
If you choose the first option, make sure you have Ollama installed and running on your laptop.
Either way, Spring AI will take care of pulling the needed Ollama models when the application starts,
if they are not available yet on your machine.

## Running the application

The application relies on the native Testcontainers support in Spring Boot to spin up
a PostgreSQL database with the pgvector extension for embeddings.

If you're using the native Ollama application, run the application as follows.

```shell
./gradlew bootTestRun
```

If you want to rely on the native Testcontainers support in Spring Boot to spin up an Ollama service at startup time,
run the application as follows.

```shell
./gradlew bootTestRun -Dspring.profiles.active=ollama-image
```

## Calling the application

> [!NOTE]
> These examples use the [httpie](https://httpie.io) CLI to send HTTP requests.

Call the application that will perform semantic searches.

```shell
http --raw "happiness" :8080/semantic-search
```
