# Structured Output: Ollama

Converting the LLM output to structured Java objects via Ollama.

## Running the application

The application relies on Ollama for providing LLMs. You can either run Ollama locally on your laptop, or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.

### Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop.
Then, use Ollama to pull the _mistral_ large language model.

```shell
ollama pull mistral
```

Finally, run the Spring Boot application.

```shell
./gradlew bootRun
```

### Ollama as a dev service with Testcontainers

The application relies on the native Testcontainers support in Spring Boot to spin up an Ollama service at startup time.

```shell
./gradlew bootTestRun
```

## Calling the application

You can now call the application that will use Ollama to generate an answer to your questions.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/chat/bean genre="rock" instrument="piano" -b
```

```shell
http :8080/chat/map genre="rock" instrument="piano" -b
```

```shell
http :8080/chat/list genre="rock" instrument="piano" -b
```
