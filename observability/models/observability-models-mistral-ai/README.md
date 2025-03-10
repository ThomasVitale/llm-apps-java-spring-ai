# LLM Observability: Mistral AI

LLM Observability for Mistral AI.

## Mistral AI

The application consumes models from the [Mistral AI](https://mistral.ai) platform.

### Create an account

Visit [console.mistral.ai](https://console.mistral.ai) and sign up for a new account.
You can choose the "Experiment" plan, which gives you access to the Mistral APIs for free.

### Configure API Key

In the Mistral AI console, navigate to _API Keys_ and generate a new API key.
Copy and securely store your API key on your machine as an environment variable.
The application will use it to access the Mistral AI API.

```shell
export MISTRALAI_API_KEY=<YOUR-API-KEY>
```

## Running the application

Run the application as follows:

```shell
./gradlew bootRun
```

Under the hood, the Arconia framework will automatically spin up a Grafana LGTM observability platform using Testcontainers.

## Observability Platform

The application logs will show you the URL where you can access the Grafana observability platform and information about logs, metrics, and traces being exported to the platform.

```logs
...o.t.grafana.LgtmStackContainer           : Access to the Grafana dashboard: http://localhost:38125
...s.l.e.o.OtlpLoggingExporterConfiguration : Configuring OpenTelemetry HTTP/Protobuf log exporter with endpoint: http://localhost:39117/v1/logs
...s.m.e.o.OtlpMetricsExporterConfiguration : Configuring OpenTelemetry HTTP/Protobuf metric exporter with endpoint: http://localhost:39117/v1/metrics
...s.t.e.o.OtlpTracingExporterConfiguration : Configuring OpenTelemetry HTTP/Protobuf span exporter with endpoint: http://localhost:39117/v1/traces
```

By default, logs, metrics, and traces are exported via OTLP using the HTTP/Protobuf format.

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

Finally, try a request which uses function calling.

```shell
http :8080/chat/functions authorName=="Philip Pullman" -b
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
