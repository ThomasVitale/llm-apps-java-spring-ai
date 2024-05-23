# Chat Models: Mistral AI

Text generation with LLMs via Mistral AI.

## Description

Spring AI provides a `ChatModel` abstraction for integrating with LLMs via several providers, including Mistral AI.

When using the _Spring AI Mistral AI Spring Boot Starter_, a `ChatModel` object is autoconfigured for you to use Mistral AI.

```java
@RestController
class ChatController {
    private final ChatModel chatModel;

    ChatController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/chat")
    String chat(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return chatModel.call(message);
    }
}
```

## Running the application

The application relies on the Mistral AI API for providing LLMs.

### When using Mistral AI

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

You can now call the application that will use Mistral AI and _open-mistral-7b_ to generate text based on a default prompt.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/chat
```

Try passing your custom prompt and check the result.

```shell
http :8080/chat message=="What is the capital of Italy?"
```

The next request is configured with a custom temperature value to obtain a more creative, yet less precise answer.

```shell
http :8080/chat/generic-options message=="Why is a raven like a writing desk? Give a short answer."
```

The next request is configured with Mistral AI-specific customizations.

```shell
http :8080/chat/mistral-ai-options message=="What can you see beyond what you can see? Give a short answer."
```
