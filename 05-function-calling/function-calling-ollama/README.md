# Function Calling: Ollama

Function calling via Ollama.

## Running the application

The application relies on Ollama for providing LLMs. You can either run Ollama locally on your laptop, or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.

### Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop.
Then, use Ollama to pull the _mistral_ large language model. That's what we'll use in this example.

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

You can now call the application that will use Ollama to call functions in order to answer questions.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/chat/function -b
```

Try passing your custom prompt and check the result.

```shell
http :8080/chat/function authorName=="Philip Pullman" -b
```

Try again. This time, the function calling strategy is configured in the call at runtime.

```shell
http :8080/chat/function/explicit authorName=="Philip Pullman" -b
```
