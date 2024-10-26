# Retrieval Augmented Generation (RAG): Advanced

Ask questions about documents with LLMs via Ollama and PGVector.

## Running the application

The application relies on Ollama for providing LLMs. You can either run Ollama locally on your laptop, or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.
Either way, Spring AI will take care of pulling the needed Ollama models if not already available in your instance.
Furthermore, the application relies on the native Testcontainers support in Spring Boot to spin up a PostgreSQL database with the pgvector extension for embeddings.
The application also relies on Testcontainers to provision automatically a Grafana LGTM observability stack.

### Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop.

Then, run the Spring Boot application.

```shell
./gradlew bootTestRun
```

### Ollama as a dev service with Testcontainers

The application relies on the native Testcontainers support in Spring Boot to spin up an Ollama service.

```shell
./gradlew bootTestRun --args='--spring.profiles.active=ollama-image'
```

## Observability Platform

Grafana is listening to port 3000. Check your container runtime to find the port to which is exposed to your localhost
and access Grafana from http://localhost:<port>. The credentials are `admin`/`admin`.

The application is automatically configured to export metrics and traces to the Grafana LGTM stack via OpenTelemetry.
In Grafana, you can query the traces from the "Explore" page, selecting the "Tempo" data source. You can also visualize metrics in "Explore > Metrics".

## Calling the application

You can now call the application that will use Ollama to load text documents as embeddings and generate an answer to your questions based on those documents (RAG pattern).
This example uses [httpie](https://httpie.io) to send HTTP requests.

### Basic

```shell
http --raw "What is Iorek's biggest dream?" :8080/rag/basic -b --pretty none
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

### Query Expansion: Multi-Query

```shell
http --raw "Who is Lucio?" :8080/rag/multi-query -b --pretty none
```

### Query Optimization: Transformation + Expansion

```shell
http --raw "Hvad er Ioreks største drøm?" :8080/rag/optimization -b --pretty none
```

### Query Routing: Keywords

```shell
http --raw "Where in Italy is Lucio going on an adventure?" :8080/rag/routing-keywords -b --pretty none
```

### Augmentation: Empty Context

```shell
http --raw "What is the capital of Denmark?" :8080/rag/empty-context -b --pretty none
```
