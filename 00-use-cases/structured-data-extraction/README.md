# Structured Data Extraction

Structured data extraction with LLMs via Ollama.

## Running the application

The application relies on Ollama for providing LLMs. You can either run Ollama locally on your laptop, or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.
Either way, Spring AI will take care of pulling the needed Ollama models if not already available in your instance.

### Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop.

Then, run the Spring Boot application.

```shell
./gradlew bootRun
```

### Ollama as a dev service with Testcontainers

The application relies on the native Testcontainers support in Spring Boot to spin up an Ollama service.

```shell
./gradlew bootTestRun
```

## Calling the application

You can now call the application that will use Ollama to extract structured data from unstructured text.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http --raw "I'm visiting Jon Snow. The blood pressure looks fine: 120/80. The temperature is 36 degrees. The diagnosis is: he knows nothing." :8080/extract
```
