# Semantic Search

Semantic search with LLMs via Ollama and PGVector.

## Running the application

The application relies on Ollama for providing LLMs. You can either run Ollama locally on your laptop, or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.
Furthermore, the application relies on the native Testcontainers support in Spring Boot to spin up a PostgreSQL database with the pgvector extension for embeddings.

### Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop.
Then, use Ollama to run the _nomic-embed-text_ large language model.

```shell
ollama run nomic-embed-text
```

Finally, run the Spring Boot application.

```shell
./gradlew bootTestRun
```

### Ollama as a dev service with Testcontainers

The application relies on the native Testcontainers support in Spring Boot to spin up an Ollama service with a _nomic-embed-text_ model at startup time.

```shell
./gradlew bootTestRun --args='--spring.profiles.active=ollama-image'
```

## Calling the application

You can now call the application that will use Ollama and _nomic-embed-text_ to perform a semantic search.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http --raw "happiness" :8080/semantic-search
```
