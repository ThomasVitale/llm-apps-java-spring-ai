# Sequential RAG: Advanced

Ask questions about documents with LLMs via Ollama and PGVector.

## Ollama

The application consumes models from an [Ollama](https://ollama.ai) inference server. You can either run Ollama locally on your laptop,
or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.
If you choose the first option, make sure you have Ollama installed and running on your laptop.
Either way, Spring AI will take care of pulling the needed Ollama models when the application starts,
if they are not available yet on your machine.

## Running the application

The application relies on Testcontainers to provision automatically
a Grafana LGTM observability stack and a PGVector database.

If you're using the native Ollama application, run the application as follows.

```shell
./gradlew bootTestRun
```

If you want to rely on the native Testcontainers support in Spring Boot to spin up an Ollama service at startup time,
run the application as follows.

```shell
./gradlew bootTestRun -Dspring.profiles.active=ollama-image
```

## Observability Platform

Grafana is listening to port 3000. Check the application logs or your container runtime to find the port to which
is exposed to your localhost and access Grafana from http://localhost:<port>. The credentials are `admin`/`admin`.

The application is automatically configured to export logs, metrics, and traces to the Grafana LGTM platform via OpenTelemetry.
In Grafana, you can query the traces from the "Explore" page, selecting the "Tempo" data source.
You can also explore metrics in "Explore > Metrics" and logs in "Explore > Logs".

## Calling the application

> [!NOTE]
> These examples use the [httpie](https://httpie.io) CLI to send HTTP requests.

Call the application that will use a chat model to answer your questions.

### Query Transformation: Translation

Without translation:

```shell
http --raw "Hvad er Ioreks største drøm?" :8080/rag/basic -b --pretty none
```

With translation:

```shell
http --raw "Hvad er Ioreks største drøm?" :8080/rag/translation -b --pretty none
```
