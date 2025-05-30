# Sequential RAG: Naive

Ask questions about documents with LLMs via Ollama and PGVector.

## Ollama

The application consumes models from an [Ollama](https://ollama.ai) inference server. You can either run Ollama locally on your laptop,
or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.
If you choose the first option, make sure you have Ollama installed and running on your laptop.
Either way, Spring AI will take care of pulling the needed Ollama models when the application starts,
if they are not available yet on your machine.

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

If you're using the native Ollama application, run the application as follows:

```shell
./gradlew bootRun
```

Under the hood, the Arconia framework will automatically spin up a PostgreSQL database using Testcontainers.

If instead you want to rely on the Ollama Dev Service via Testcontainers, run the application as follows.

```shell
./gradlew bootRun -Darconia.dev.services.ollama.enabled=true
```

## Calling the application

> [!NOTE]
> These examples use the [httpie](https://httpie.io) CLI to send HTTP requests.

Call the application that will use a chat model to answer your questions.

```shell
http --raw "What is Iorek's biggest dream?" :8080/rag/vector-store -b --pretty none
```

```shell
http --raw "Who is Lucio?" :8080/rag/vector-store -b --pretty none
```

By default, if you ask questions not related to the documents, the model will say it doesn't know the answer.

```shell
http --raw "What is the capital of Denmark?" :8080/rag/vector-store -b --pretty none
```

You can allow the model to answer questions not related to the documents, that is when no document is retrieved from the vector store.

```shell
http --raw "What is the capital of Denmark?" :8080/rag/empty-context -b --pretty none
```

Instead of retrieving context from a vector store, you can query a web search engine and ask questions about current events or general knowledge not covered by the model training data.

```shell
http --raw "At the Spring I/O 2025 conference in Barcelona, who is the speaker presenting about Modular RAG?" :8080/rag/search-engine -b --pretty none
```
