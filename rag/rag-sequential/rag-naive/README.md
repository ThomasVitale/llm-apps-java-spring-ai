# Sequential RAG: Naive

Ask questions about documents with LLMs via Ollama and PGVector.

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

Call the application that will use a chat model to answer your questions.

```shell
http --raw "What is Iorek's biggest dream?" :8080/rag/basic -b --pretty none
```

```shell
http --raw "Who is Lucio?" :8080/rag/basic -b --pretty none
```

By default, if you ask questions not related to the documents, the model will say it doesn't know the answer.

```shell
http --raw "What is the capital of Denmark?" :8080/rag/basic -b --pretty none
```

You can allow the model to answer questions not related to the documents, that is when no document is retrieved from the vector store.

```shell
http --raw "What is the capital of Denmark?" :8080/rag/empty-context -b --pretty none
```
