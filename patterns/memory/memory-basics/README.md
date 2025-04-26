# Memory Basics

Using chat memory with LLMs via Ollama.

## Ollama

The application consumes models from an [Ollama](https://ollama.ai) inference server. You can either run Ollama locally on your laptop,
or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.
If you choose the first option, make sure you have Ollama installed and running on your laptop.
Either way, Spring AI will take care of pulling the needed Ollama models when the application starts,
if they are not available yet on your machine.

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

### Messages

```shell
http --raw "My name is Bond. James Bond." :8080/memory/messages/007 -b --pretty none
```

```shell
http --raw "What's my name?" :8080/memory/messages/007 -b --pretty none
```

### Prompt

```shell
http --raw "What's the meaning of life? Answer in one sentence." :8080/memory/prompt/42 -b --pretty none
```

```shell
http --raw "What did I just ask you" :8080/memory/prompt/420 -b --pretty none
```
