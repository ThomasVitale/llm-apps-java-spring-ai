# Question Answering (RAG)

Ask questions about documents with LLMs via Ollama and PGVector.

## Running the application

The application relies on Ollama for providing LLMs. You can either run Ollama locally on your laptop, or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.
Either way, Spring AI will take care of pulling the needed Ollama models if not already available in your instance.
Furthermore, the application relies on the native Testcontainers support in Spring Boot to spin up a PostgreSQL database with the pgvector extension for embeddings.

### Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop.

Then, run the Spring Boot application.

```shell
./gradlew bootTestRun
```

### Ollama as a dev service with Testcontainers

The application relies on the native Testcontainers support in Spring Boot to spin up an Ollama service.

```shell
./gradlew bootTestRun --args='--spring.profiles.active=ollama-image'
```

## Calling the application

You can now call the application that will use Ollama to load text documents as embeddings and generate an answer to your questions based on those documents (RAG pattern).
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http --raw "What is Iorek's biggest dream?" :8080/chat/doc
```

```shell
http --raw "Who is Lucio?" :8080/chat/doc
```
