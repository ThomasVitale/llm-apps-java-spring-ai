# PDF Document Readers: Ollama

Reading and vectorizing PDF documents with LLMs via Ollama.

# Running the application

The application relies on Ollama for providing LLMs. You can either run Ollama locally on your laptop (macOS or Linux), or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.

### When using Ollama

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop (macOS or Linux).
Then, use Ollama to run the _llama2_ large language model.

```shell
ollama run llama2
```

Finally, run the Spring Boot application.

```shell
./gradlew bootRun
```

### When using Docker/Podman

The application relies on the native Testcontainers support in Spring Boot to spin up an Ollama service with a _llama2_ model at startup time.

```shell
./gradlew bootTestRun
```

## Calling the application

You can now call the application that will use Ollama and llama2 to load PDF documents as embeddings and generate an answer to your questions based on those documents (RAG pattern).
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http --raw "What is Iorek's biggest dream?" :8080/ai/doc/chat
```

```shell
http --raw "Who is Lucio?" :8080/ai/doc/chat
```
