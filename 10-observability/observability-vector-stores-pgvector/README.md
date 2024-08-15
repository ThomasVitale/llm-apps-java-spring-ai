# Vector Store Observability: PGVector

Vector Store Observability for PGVector.

## Running the application

The application relies on an OpenAI API for providing LLMs. This example guides you to use either the OpenAI Platform
or Ollama via the OpenAI-compatible API. The application also relies on Testcontainers to provision automatically
a Grafana LGTM observability stack and a PGVector database.

### When using OpenAI

First, make sure you have an OpenAI account.
Then, define an environment variable with the OpenAI API Key associated to your OpenAI account as the value.

```shell
export SPRING_AI_OPENAI_API_KEY=<INSERT KEY HERE>
```

Finally, run the Spring Boot application.

```shell
./gradlew bootTestRun
```

### When using Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop.
Then, use Ollama to run the _mistral_ and _nomic-embed-text_ models. Those are the ones we'll use in this example.

```shell
ollama run mistral
ollama run nomic-embed-text
```

Finally, run the Spring Boot application.

```shell
./gradlew bootTestRun --args='--spring.profiles.active=ollama'
```

## Observability Platform

Grafana is listening to port 3000. Check your container runtime to find the port to which is exposed to your localhost
and access Grafana from http://localhost:<port>. The credentials are `admin`/`admin`.

The application is automatically configured to export metrics and traces to the Grafana LGTM stack via OpenTelemetry.
In Grafana, you can query the traces from the "Explore" page, selecting the "Tempo" data source. You can also visualize metrics in "Explore > Metrics".

## Calling the application

You can now call the application to perform generative AI operations.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http --raw "What is Iorek's biggest dream?" :8080/chat/doc
```

```shell
http --raw "Who is Lucio?" :8080/chat/doc
```
