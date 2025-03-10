# Text Classification

Text classification with LLMs via Ollama.

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

Call the application that will use a chat model to classify text.

Each endpoint is backed by a progressively better prompt to increase the quality of the text classification task.

Class Names:

```shell
http --raw "Basketball fans can now watch the game on the brand-new NBA app for Apple Vision Pro." :8080/classify/class-names
```

Class Descriptions:

```shell
http --raw "Basketball fans can now watch the game on the brand-new NBA app for Apple Vision Pro." :8080/classify/class-descriptions
```

Few Shots Prompt:

```shell
http --raw "Basketball fans can now watch the game on the brand-new NBA app for Apple Vision Pro." :8080/classify/few-shots-prompt
```

Few Shots History:

```shell
http --raw "Basketball fans can now watch the game on the brand-new NBA app for Apple Vision Pro." :8080/classify/few-shots-history
```

Structured Output:

```shell
http --raw "Basketball fans can now watch the game on the brand-new NBA app for Apple Vision Pro." :8080/classify/structured-output
```
