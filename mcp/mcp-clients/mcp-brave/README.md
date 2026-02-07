# Tools: Brave + Model Context Protocol

Integrating with the Brave Search API via the Model Context Protocol.

## Brave

The application consumes the [Brave Search API](https://api.search.brave.com).

### Create an account

Visit [api.search.brave.com](https://api.search.brave.com) and sign up for a new account.
Then, in the Brave Search API console, navigate to _Subscriptions_ and choose a subscription plan.
You can choose the "Free AI" plan to get started.

### Configure API Key

In the Brave Search API console, navigate to _API Keys_ and generate a new API key.
Copy and securely store your API key on your machine as an environment variable.
The application will use it to access the Brave Search API.

```shell
export BRAVE_SEARCH_API_KEY=<YOUR-API-KEY>
```

## Ollama

The application consumes models from an [Ollama](https://ollama.ai) inference server. You can either run Ollama locally on your laptop, or let Arconia provide a Dev Service that will run Ollama as a container automatically.

Either way, Spring AI will take care of pulling the needed Ollama models when the application starts, if they are not available yet on your machine.

## Running the application

Run the application as follows:

```shell
./gradlew bootRun
```

Under the hood, in case no native Ollama connection is detected on your machine, the Arconia framework will automatically spin up an [Ollama](https://arconia.io/docs/arconia/latest/dev-services/ollama/) inference service using Testcontainers (see [Arconia Dev Services](https://arconia.io/docs/arconia/latest/dev-services/) for more information).

The application will be accessible at http://localhost:8080.

## Calling the application

> [!NOTE]
> These examples use the [httpie](https://httpie.io) CLI to send HTTP requests.

Call the application that will use an MCP Server to retrieve the context to answer your question.

```shell
http :8080/chat/mcp question=="Does Spring AI supports a Modular RAG architecture? Please provide some references."
```
