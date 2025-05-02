# Memory with Spring Security

Using chat memory with LLMs via Ollama. Conversations are tracked by user ID and are secured with Spring Security.

## Ollama

The application consumes models from an [Ollama](https://ollama.ai) inference server. You can either run Ollama locally on your laptop, or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically. If you choose the first option, make sure you have Ollama installed and running on your laptop. Either way, Spring AI will take care of pulling the needed Ollama models when the application starts, if they are not available yet on your machine.

## Running the application

If you're using the native Ollama application, run the application as follows:

```shell
./gradlew bootRun
```

If instead you want to rely on the Ollama Dev Service via Testcontainers, run the application as follows.

```shell
./gradlew bootRun -Darconia.dev.services.ollama.enabled=true
```

## Calling the application

> [!NOTE]
> These examples use the [httpie](https://httpie.io) CLI to send HTTP requests.

Call the application that will use a chat model to answer your question.

### User 1

```shell
http -a george:lizard --raw "My name is Bond. James Bond." :8080/memory/security -b --pretty none
```

```shell
http -a george:lizard --raw "What's my name?" :8080/memory/security -b --pretty none
```

### User 2

```shell
http -a isabella:butterfly --raw "What's my name?" :8080/memory/security -b --pretty none
```
