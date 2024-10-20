# LLM Observability: Ollama

LLM Observability for Ollama.

## Running the application

The application relies on Ollama for providing LLMs. Spring AI will take care of pulling the needed Ollama models if not already available in your instance.

The application also relies on Testcontainers to provision automatically a Grafana LGTM observability stack.

### Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop.

Then, run the Spring Boot application.

```shell
./gradlew bootTestRun
```

## Observability Platform

Grafana is listening to port 3000. Check your container runtime to find the port to which is exposed to your localhost
and access Grafana from http://localhost:<port>. The credentials are `admin`/`admin`.

The application is automatically configured to export metrics and traces to the Grafana LGTM stack via OpenTelemetry.
In Grafana, you can query the traces from the "Explore" page, selecting the "Tempo" data source. You can also visualize metrics in "Explore > Metrics".

## Calling the application

You can now call the application to perform generative AI operations.
This example uses [httpie](https://httpie.io) to send HTTP requests.

### Chat

```shell
http :8080/chat
```

Try passing your custom prompt and check the result.

```shell
http :8080/chat message=="What is the capital of Italy?"
```

The next request is configured with a custom temperature value to obtain a more creative, yet less precise answer.

```shell
http :8080/chat/generic-options message=="Why is a raven like a writing desk? Give a short answer."
```

The next request is configured with Ollama-specific customizations.

```shell
http :8080/chat/ollama-options message=="What can you see beyond what you can see? Give a short answer."
```

Finally, try a request which uses function calling.

```shell
http :8080/chat/functions authorName=="Philip Pullman"
```

### Embedding

```shell
http :8080/embed
```

Try passing your custom prompt and check the result.

```shell
http :8080/embed message=="The capital of Italy is Rome"
```

The next request is configured with Ollama-specific customizations.

```shell
http :8080/embed/ollama-options message=="The capital of Italy is Rome"
```
