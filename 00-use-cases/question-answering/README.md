# Question Answering (RAG)

Ask questions about documents with LLMs via Ollama and PGVector.

## Ollama

The application relies on Ollama for providing LLMs. You can either run Ollama locally on your laptop,
or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.
If you choose the first option, make sure you have [Ollama](https://ollama.ai) installed and running on your laptop.
Either way, Spring AI will take care of pulling the needed Ollama models when the application starts,
if they are not available yet on your machine.
Furthermore, the application relies on the native Testcontainers support in Spring Boot to spin up
a PostgreSQL database with the pgvector extension for embeddings.

## Running the application

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

Call the application that will use a chat model to answer your questions.

```shell
http --raw "What is Iorek's biggest dream?" :8080/chat/doc
```

```shell
http --raw "Who is Lucio?" :8080/chat/doc
```
