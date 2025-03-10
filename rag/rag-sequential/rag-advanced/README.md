# Sequential RAG: Advanced

Ask questions about documents with LLMs via Ollama and PGVector.

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

Under the hood, the Arconia framework will automatically spin up a PostgreSQL database and a Grafana LGTM observability platform using Testcontainers.

If instead you want to rely on the Ollama Dev Service via Testcontainers, run the application as follows.

```shell
./gradlew bootRun -Darconia.dev.services.ollama.enabled=true
```

## Observability Platform

The application logs will show you the URL where you can access the Grafana observability platform and information about logs, metrics, and traces being exported to the platform.

```logs
...o.t.grafana.LgtmStackContainer           : Access to the Grafana dashboard: http://localhost:38125
...s.l.e.o.OtlpLoggingExporterConfiguration : Configuring OpenTelemetry HTTP/Protobuf log exporter with endpoint: http://localhost:39117/v1/logs
...s.m.e.o.OtlpMetricsExporterConfiguration : Configuring OpenTelemetry HTTP/Protobuf metric exporter with endpoint: http://localhost:39117/v1/metrics
...s.t.e.o.OtlpTracingExporterConfiguration : Configuring OpenTelemetry HTTP/Protobuf span exporter with endpoint: http://localhost:39117/v1/traces
```

By default, logs, metrics, and traces are exported via OTLP using the HTTP/Protobuf format.

In Grafana, you can query the traces from the "Explore" page, selecting the "Tempo" data source.
You can also explore metrics in "Explore > Metrics" and logs in "Explore > Logs".

## Calling the application

> [!NOTE]
> These examples use the [httpie](https://httpie.io) CLI to send HTTP requests.

Call the application that will use a chat model to answer your questions.

### Query Transformation: Compression

Without compression:

```shell
http --raw "Who are the characters going on an adventure in the North Pole?" :8080/rag/memory/007 -b --pretty none
```

```shell
http --raw "What places do they visit?" :8080/rag/memory/007 -b --pretty none
```

With compression:

```shell
http --raw "Who are the characters going on an adventure in the North Pole?" :8080/rag/compression/007 -b --pretty none
```

```shell
http --raw "What places do they visit?" :8080/rag/compression/007 -b --pretty none
```

### Query Transformation: Rewrite

Without rewrite:

```shell
http --raw "Where are the main characters going on an adventure?" :8080/rag/basic -b --pretty none
```

With rewrite:

```shell
http --raw "Where are the main characters going on an adventure?" :8080/rag/rewrite -b --pretty none
```

### Query Transformation: Translation

Without translation:

```shell
http --raw "Hvad er Ioreks største drøm?" :8080/rag/basic -b --pretty none
```

With translation:

```shell
http --raw "Hvad er Ioreks største drøm?" :8080/rag/translation -b --pretty none
```
