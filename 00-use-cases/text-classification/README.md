# Text Classification

Text classification with LLMs via Ollama.

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

The application relies on the native Testcontainers support in Spring Boot to spin up an Ollama service with a _mistral_ model at startup time.

```shell
./gradlew bootTestRun
```

## Calling the application

You can now call the application that will use Ollama and _mistral_ to classify your text.
This example uses [httpie](https://httpie.io) to send HTTP requests.

Each endpoint is backed by a progressively better prompt to increase the quality of the text classification task by the LLM.

Class Names:

```shell
http --raw "NBA announced a new application to enhance the experience of watching basketball on Apple Vision Pro." :8080/classify/class-names
```

Class Descriptions:

```shell
http --raw "NBA announced a new application to enhance the experience of watching basketball on Apple Vision Pro." :8080/classify/class-descriptions
```

Few Shots Prompt:

```shell
http --raw "NBA announced a new application to enhance the experience of watching basketball on Apple Vision Pro." :8080/classify/few-shots-prompt
```

Few Shots History:

```shell
http --raw "NBA announced a new application to enhance the experience of watching basketball on Apple Vision Pro." :8080/classify/few-shots-history
```

Structured Output:

```shell
http --raw "NBA announced a new application to enhance the experience of watching basketball on Apple Vision Pro." :8080/classify/structured-output
```
