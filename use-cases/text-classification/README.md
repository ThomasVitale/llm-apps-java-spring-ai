# Text Classification

Text classification with LLMs via Ollama.

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

Call the application that will use a chat model to classify text.

Each endpoint is backed by a progressively better prompt to increase the quality of the text classification task.

Class Names:

```shell
http --raw "Basketball fans can now watch the game on the brand-new NBA app for Apple Vision Pro." :8080/classify/class-names -b --pretty none
```

Class Descriptions:

```shell
http --raw "Basketball fans can now watch the game on the brand-new NBA app for Apple Vision Pro." :8080/classify/class-descriptions -b --pretty none
```

Few Shots Prompt:

```shell
http --raw "Basketball fans can now watch the game on the brand-new NBA app for Apple Vision Pro." :8080/classify/few-shots-prompt -b --pretty none
```

Few Shots History:

```shell
http --raw "Basketball fans can now watch the game on the brand-new NBA app for Apple Vision Pro." :8080/classify/few-shots-history -b --pretty none
```

Structured Output:

```shell
http --raw "Basketball fans can now watch the game on the brand-new NBA app for Apple Vision Pro." :8080/classify/structured-output
```
