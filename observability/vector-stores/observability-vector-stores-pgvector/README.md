# Vector Store Observability: PGVector

Vector Store Observability for PGVector.

## OpenAI

The application consumes models from the [OpenAI](https://openai.com) platform.

### Create an account

Visit [platform.openai.com](https://platform.openai.com) and sign up for a new account.

### Configure API Key

In the OpenAI console, navigate to _Dashboard > API Keys_ and generate a new API key.
Copy and securely store your API key on your machine as an environment variable.
The application will use it to access the OpenAI API.

```shell
export OPENAI_API_KEY=<YOUR-API-KEY>
```

## Running the application

Run the application as follows:

```shell
./gradlew bootRun
```

Under the hood, the Arconia framework will automatically spin up a PGVector database and a Grafana LGTM observability platform using Testcontainers.

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

```shell
http --raw "What is Iorek's biggest dream?" :8080/chat/doc
```

```shell
http --raw "Who is Lucio?" :8080/chat/doc
```
