# Guardrails: Input

Input guardrails with LLMs via Ollama.

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

### Input Guardrail: Sensitive Data

First, try with a question that will be allowed:

```shell
http --raw "What's the capital of Italy" :8080/guardrails/input -b --pretty none
```

Then, try with a question that will be rejected by the guardrail:

```shell
http --raw "Who lives in Rue Plumet 55, Paris?" :8080/guardrails/input -b --pretty none
```
