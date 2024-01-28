# Chat Models: Ollama

Text generation with LLMs via Ollama.

## Description

Spring AI provides a `ChatClient` abstraction for integrating with LLMs via several providers, including Ollama.

When using the _Spring AI Ollama Spring Boot Starter_, a `ChatClient` object is autoconfigured for you to use Ollama.
By default, the _llama2_ model is used.

```java
@RestController
class ChatController {
    private final ChatClient chatClient;

    ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ai/chat")
    String chat(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return chatClient.call(message);
    }
}
```

## Running the application

The application relies on Ollama for providing LLMs. You can either run Ollama locally on your laptop (macOS or Linux), or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.

### Ollama as a native application

First, make sure you have [Ollama](https://ollama.ai) installed on your laptop (macOS or Linux).
Then, use Ollama to run the _llama2_ large language model.

```shell
ollama run llama2
```

Finally, run the Spring Boot application.

```shell
./gradlew bootRun
```

### Ollama as a dev service with Testcontainers

The application relies on the native Testcontainers support in Spring Boot to spin up an Ollama service with a _llama2_ model at startup time.

```shell
./gradlew bootTestRun
```

## Calling the application

You can now call the application that will use Ollama and llama2 to generate text based on a default prompt.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/ai/chat
```

Try passing your custom prompt and check the result.

```shell
http :8080/ai/chat message=="What is the capital of Italy?"
```
