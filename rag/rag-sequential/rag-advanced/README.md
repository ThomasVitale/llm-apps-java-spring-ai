# Sequential RAG: Advanced

Ask questions about documents with LLMs via Ollama and PGVector.

## Ollama

The application consumes models from an [Ollama](https://ollama.ai) inference server. You can either run Ollama locally on your laptop, or let Arconia provide a Dev Service that will run Ollama as a container automatically.

Either way, Spring AI will take care of pulling the needed Ollama models when the application starts, if they are not available yet on your machine.

## Running the application

Run the application as follows:

```shell
./gradlew bootRun
```

Under the hood, in case no native Ollama connection is detected on your machine, the Arconia framework will automatically spin up an [Ollama](https://arconia.io/docs/arconia/latest/dev-services/ollama/) inference service using Testcontainers. It will also automatically provision a [PostgreSQL](https://arconia.io/docs/arconia/latest/dev-services/postgresql/) database and a [Grafana LGTM](https://arconia.io/docs/arconia/latest/dev-services/lgtm/) observability platform. See [Arconia Dev Services](https://arconia.io/docs/arconia/latest/dev-services/) for more information.

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
http --raw "Who are the characters going on an adventure in the North Pole?" :8080/rag/query/compression/007 -b --pretty none
```

```shell
http --raw "What places do they visit?" :8080/rag/query/compression/007 -b --pretty none
```

### Query Transformation: Rewrite

Without rewrite:

```shell
http --raw "Where are the main characters going on an adventure?" :8080/rag/vector-store -b --pretty none
```

With rewrite:

```shell
http --raw "Where are the main characters going on an adventure?" :8080/rag/query/rewrite -b --pretty none
```

### Query Transformation: Translation

Without translation:

```shell
http --raw "Hvad er Ioreks største drøm?" :8080/rag/vector-store -b --pretty none
```

With translation:

```shell
http --raw "Hvad er Ioreks største drøm?" :8080/rag/query/translation -b --pretty none
```

### Document Post-Processing: Compression

Without compression:

```shell
http --raw "Who is Lucio?" :8080/rag/vector-store -b --pretty none
```

With compression:

```shell
http --raw "Who is Lucio?" :8080/rag/docs/compression -b --pretty none
```
