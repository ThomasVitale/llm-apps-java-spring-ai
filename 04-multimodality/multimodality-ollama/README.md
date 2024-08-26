# Prompts with Multimodality: Ollama

Multimodality with LLMs via Ollama.

## Running the application

The application relies on Ollama for providing LLMs. You can either run Ollama locally on your laptop, or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.

### Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop.
Then, use Ollama to pull the _llava_ large language model. That's what we'll use in this example.

```shell
ollama pull llava
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

You can now call the application that will use Ollama to generate text based on a default image.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/chat/image/file -b
```

Try passing your custom prompt and check the result.

```shell
http :8080/chat/image/file question=="Is there an animal in the picture?" -b
```
