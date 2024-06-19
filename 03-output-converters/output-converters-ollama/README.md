# Output Converters: Ollama

Converting the LLM output to structured objects (Beans, Map, List) via Ollama.

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

You can now call the application that will use Ollama and _mistral_ to generate an answer to your questions.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/chat/bean genre="rock" instrument="piano"
```

```shell
http :8080/chat/map genre="rock" instrument="piano"
```

```shell
http :8080/chat/list genre="rock" instrument="piano"
```
