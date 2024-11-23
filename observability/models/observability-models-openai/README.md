# LLM Observability: OpenAI

LLM Observability for OpenAI.

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

The application relies on the native Testcontainers support in Spring Boot to spin up a Grafana LGTM observability platform.
Run the application as follows:

```shell
./gradlew bootTestRun
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

### Image

Call the application that will use an image model to generate an image.

```shell
http :8080/image message=="Here comes the sun"
```

The next request is configured with provider-specific customizations.

```shell
http :8080/image/provider-options message=="Here comes the sun"
```
