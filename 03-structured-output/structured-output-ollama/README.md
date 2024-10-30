# Structured Output: Ollama

Converting the LLM output to structured Java objects via Ollama.

## Ollama

The application relies on Ollama for providing LLMs. You can either run Ollama locally on your laptop,
or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.
If you choose the first option, make sure you have [Ollama](https://ollama.ai) installed and running on your laptop.
Either way, Spring AI will take care of pulling the needed Ollama models when the application starts,
if they are not available yet on your machine.

## Running the application

If you're using the native Ollama application, run the application as follows.

```shell
./gradlew bootRun
```

If you want to rely on the native Testcontainers support in Spring Boot to spin up an Ollama service at startup time,
run the application as follows.

```shell
./gradlew bootTestRun
```

## Calling the application

> [!NOTE]
> These examples use the [httpie](https://httpie.io) CLI to send HTTP requests.

Call the application that will use a chat model to answer your question.

```shell
http :8080/chat/bean genre="rock" instrument="piano" -b
```

```shell
http POST :8080/chat/map -b
```

```shell
http :8080/chat/list genre="rock" instrument="piano" -b
```
