# Conditional RAG: Routing

Ask questions about documents with LLMs via Ollama and PGVector.

## Ollama

The application consumes models from an [Ollama](https://ollama.ai) inference server. You can either run Ollama locally on your laptop, or let Arconia provide a Dev Service that will run Ollama as a container automatically.

Either way, Spring AI will take care of pulling the needed Ollama models when the application starts, if they are not available yet on your machine.

### Tavily (Optional)

The application consumes the [Tavily Search API](https://tavily.com).

#### Create an account

Visit [app.tavily.com](https://app.tavily.com/home) and sign up for a new account. You can choose the "Researcher" plan to get started for free.

#### Configure API Key

In the Tavily AI console, navigate to _Overview_. Then, in the _API Keys_ section, generate a new API key. Copy and securely store your API key on your machine as an environment variable. The application will use it to access the Tavily Search API.

```shell
export TAVILY_SEARCH_API_KEY=<YOUR-API-KEY>
```

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

### Query Routing

First, ask a question that should be routed to the first RAG flow for the first story using a vector store.

```shell
http --raw "What is Iorek's biggest dream?" :8080/rag/query/routing -b --pretty none
```

Then, ask a question that should be routed to the second RAG flow for the second story using a vector store.

```shell
http --raw "Who is Lucio?" :8080/rag/query/routing -b --pretty none
```

Finally, ask a question that should be routed to the third RAG flow using a web search engine.

```shell
http --raw "At the Spring I/O 2025 conference in Barcelona, who is the speaker presenting about Modular RAG?" :8080/rag/query/routing -b --pretty none
```