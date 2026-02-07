# Chat Models: Ollama

Chat completion with LLMs via Ollama.

## Description

Spring AI provides a `ChatModel` low-level abstraction for integrating with LLMs via several providers, including Ollama.

When using the _Spring AI Ollama Spring Boot Starter_, a `ChatModel` object is autoconfigured for you to use Ollama.

```java
@Bean
CommandLineRunner chat(ChatModel chatModel) {
    return _ -> {
        var response = chatModel.call("What is the capital of Italy?");
        System.out.println(response);
    };
}
```

Spring AI also provides a higher-level abstraction for building more advanced LLM workflows: `ChatClient`.
A `ChatClient.Builder` object is autoconfigured for you to build a `ChatClient` object. Under the hood, it relies on a `ChatModel`.

```java
@Bean
CommandLineRunner chat(ChatClient.Builder chatClientBuilder) {
    var chatClient = chatClientBuilder.build();
    return _ -> {
        var response = chatClient
                .prompt("What is the capital of Italy?")
                .call()
                .content();
        System.out.println(response);
    };
}
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

The final request returns the model's answer as a stream.

```shell
http --stream :8080/chat/stream question=="Why is a raven like a writing desk? Answer in 3 paragraphs." -b
```

Ollama lets you run models directly from Hugging Face. Let's try that out.

```shell
http :8080/chat/huggingface question=="What is the capital of Italy?" -b
```
