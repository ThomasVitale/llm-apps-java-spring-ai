# Chat Models: Ollama

Text generation with LLMs via Ollama.

## Description

Spring AI provides a `ChatModel` low-level abstraction for integrating with LLMs via several providers, including Ollama.

When using the _Spring AI Ollama Spring Boot Starter_, a `ChatModel` object is autoconfigured for you to use Ollama.

```java
@RestController
class ChatController {
    private final ChatModel chatModel;

    ChatController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/chat")
    String chat(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return chatModel.call(question);
    }
}
```

Spring AI also provides a higher-level abstraction for building more advanced LLM workflows: `ChatClient`.
A `ChatClient.Builder` object is autoconfigured for you to build a `ChatClient` object. Under the hood, it relies on a `ChatModel`.

```java
@RestController
class ChatController {
    private final ChatClient chatClient;

    ChatClientController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    String chat(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}
```

## Running the application

The application relies on Ollama for providing LLMs. You can either run Ollama locally on your laptop, or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.

### Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop.
Then, use Ollama to pull the _mistral_ large language model.

```shell
ollama pull mistral
```

Finally, run the Spring Boot application.

```shell
./gradlew bootRun
```

### Ollama as a dev service with Testcontainers

The application relies on the native Testcontainers support in Spring Boot to spin up an Ollama service at startup time.

```shell
./gradlew bootTestRun
```

## Calling the application

You can now call the application that will use Ollama to generate text based on a default prompt.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/chat -b
```

Try passing your custom prompt and check the result.

```shell
http :8080/chat question=="What is the capital of Italy?" -b
```

The next request is configured with a custom temperature value to obtain a more creative, yet less precise answer.

```shell
http :8080/chat/generic-options question=="Why is a raven like a writing desk? Give a short answer." -b
```

The next request is configured with Ollama-specific customizations.

```shell
http :8080/chat/provider-options question=="What can you see beyond what you can see? Give a short answer." -b
```

The final request returns the model's answer as a stream.

```shell
http --stream :8080/chat/stream question=="Why is a raven like a writing desk? Answer in 3 paragraphs." -b
```
