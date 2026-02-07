# Tool Calling: Ollama

Tool calling via Ollama.

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

Try the method-based tool calling strategy.

```shell
http :8080/chat/method/no-args -b
```

```shell
http :8080/chat/method/no-args-stream -b
```

```shell
http :8080/chat/method/void user=="Jessica" -b
```

```shell
http :8080/chat/method/single authorName=="J.R.R. Tolkien" -b
```

```shell
http :8080/chat/method/single authorName=="C.S. Lewis" -b
```

```shell
http :8080/chat/method/list bookTitle1=="Narnia" bookTitle2=="The Hobbit" -b
```

Next, try the function-based tool calling strategy.

```shell
http :8080/chat/function/void-input -b
```

```shell
http :8080/chat/function/void-input/callback -b
```

```shell
http :8080/chat/function/void-output user=="Jessica" -b
```

```shell
http :8080/chat/function/void-output/callback user=="Jessica" -b
```

```shell
http :8080/chat/function/single authorName=="J.R.R. Tolkien" -b
```

```shell
http :8080/chat/function/single/callback authorName=="J.R.R. Tolkien" -b
```

```shell
http :8080/chat/function/list bookTitle1=="Narnia" bookTitle2=="The Hobbit" -b
```

```shell
http :8080/chat/function/list/callback bookTitle1=="Narnia" bookTitle2=="The Hobbit" -b
```
