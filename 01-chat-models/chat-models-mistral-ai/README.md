# Chat Models: Mistral AI

Text generation with LLMs via Mistral AI.

## Description

Spring AI provides a `ChatModel` low-level abstraction for integrating with LLMs via several providers, including Mistral AI.

When using the _Spring AI Mistral AI Spring Boot Starter_, a `ChatModel` object is autoconfigured for you to use Mistral AI.

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

The application relies on the Mistral AI API for providing LLMs.

First, make sure you have a [Mistral AI account](https://console.mistral.ai).
Then, define an environment variable with the Mistral AI API Key associated to your Mistral AI account as the value.

```shell
export SPRING_AI_MISTRALAI_API_KEY=<INSERT KEY HERE>
```

Finally, run the Spring Boot application.

```shell
./gradlew bootRun
```

## Calling the application

You can now call the application that will use Mistral AI to generate text based on a default prompt.
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

The next request is configured with Mistral AI-specific customizations.

```shell
http :8080/chat/provider-options question=="What can you see beyond what you can see? Give a short answer." -b
```

The final request returns the model's answer as a stream.

```shell
http --stream :8080/chat/stream question=="Why is a raven like a writing desk? Answer in 3 paragraphs." -b
```
