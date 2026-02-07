# Memory Basics

Using chat memory with LLMs via Ollama.

## Ollama

The application consumes models from an [Ollama](https://ollama.ai) inference server. You can either run Ollama locally on your laptop, or let Arconia provide a Dev Service that will run Ollama as a container automatically.

Either way, Spring AI will take care of pulling the needed Ollama models when the application starts, if they are not available yet on your machine.

## Running the application

Run the application as follows:

```shell
./gradlew bootRun
```

Under the hood, in case no native Ollama connection is detected on your machine, the Arconia framework will automatically spin up an [Ollama](https://arconia.io/docs/arconia/latest/dev-services/ollama/) inference service using Testcontainers (see [Arconia Dev Services](https://arconia.io/docs/arconia/latest/dev-services/) for more information).

The application will be accessible at http://localhost:8080.

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
