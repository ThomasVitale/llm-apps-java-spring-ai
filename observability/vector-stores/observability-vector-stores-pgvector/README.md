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

The application relies on Testcontainers to provision automatically
a Grafana LGTM observability stack and a PGVector database.

Run the application as follows.

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

Call the application that will use a chat model to answer your questions.

```shell
http --raw "What is Iorek's biggest dream?" :8080/chat/doc
```

```shell
http --raw "Who is Lucio?" :8080/chat/doc
```
