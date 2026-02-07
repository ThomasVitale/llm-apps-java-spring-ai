# LLM Observability: Ollama

LLM Observability for Ollama.

## Ollama

The application consumes models from an [Ollama](https://ollama.ai) inference server. You can either run Ollama locally on your laptop, or let Arconia provide a Dev Service that will run Ollama as a container automatically.

Either way, Spring AI will take care of pulling the needed Ollama models when the application starts, if they are not available yet on your machine.

## Running the application

Run the application as follows:

```shell
./gradlew bootRun
```

Under the hood, in case no native Ollama connection is detected on your machine, the Arconia framework will automatically spin up an [Ollama](https://arconia.io/docs/arconia/latest/dev-services/ollama/) inference service using Testcontainers. It will also automatically provision a [Grafana LGTM](https://arconia.io/docs/arconia/latest/dev-services/lgtm/) observability platform. See [Arconia Dev Services](https://arconia.io/docs/arconia/latest/dev-services/) for more information.

The application will be accessible at http://localhost:8080.

## Observability Platform

The application logs will show you the URL where you can access the Grafana observability platform and start exploring your application’s telemetry data.

```logs
...o.t.grafana.LgtmStackContainer: Access to the Grafana dashboard: http://localhost:<port>
```

By default, logs, metrics, and traces are exported via OTLP using the HTTP/Protobuf format.

In Grafana, you can query the telemetry from the "Drilldown" and "Explore" sections.

## Calling the application

> [!NOTE]
> These examples use the [httpie](https://httpie.io) CLI to send HTTP requests.

### Chat

Call the application that will use a chat model to answer your question.

```shell
http :8080/chat question=="What is the capital of Italy?" -b
```

The next request is configured with generic portable options.

```shell
http :8080/chat/generic-options question=="Why is a raven like a writing desk? Give a short answer." -b
```

The next request is configured with the provider's specific options.

```shell
http :8080/chat/provider-options question=="What can you see beyond what you can see? Give a short answer." -b
```

The next request returns the model's answer as a stream.

```shell
http --stream :8080/chat/stream question=="Why is a raven like a writing desk? Answer in 3 paragraphs." -b
```

Finally, try a request which uses tools.

```shell
http :8080/chat/tools authorName=="Philip Pullman" -b
```

### Embedding

Call the application that will use an embedding model to generate embeddings for your query.

```shell
http :8080/embed query=="The capital of Italy is Rome"
```

The next request is configured with generic portable options.

```shell
http :8080/embed/generic-options query=="The capital of Italy is Rome" -b
```

The next request is configured with the provider's specific options.

```shell
http :8080/embed/provider-options query=="The capital of Italy is Rome" -b
```
