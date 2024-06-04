# Structured Data Extraction

Structured data extraction with LLMs via Ollama.

## Running the application

The application relies on Ollama for providing LLMs. You can either run Ollama locally on your laptop, or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.

### Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop.
Then, use Ollama to run the _mistral_ large language model.

```shell
ollama run mistral
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

You can now call the application that will use Ollama and _mistral_ to extract structured data from unstructured text.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http --raw "I'm visiting Jon Snow. The blood pressure looks fine: 120/80. The temperature is 36 degrees. The diagnosis is: he knows nothing." :8080/extract
```
