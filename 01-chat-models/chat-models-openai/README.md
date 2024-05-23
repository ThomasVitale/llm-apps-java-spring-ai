# Chat Models: OpenAI

Text generation with LLMs via OpenAI.

## Description

Spring AI provides a `ChatModel` abstraction for integrating with LLMs via several providers, including OpenAI.

When using the _Spring AI OpenAI Spring Boot Starter_, a `ChatModel` object is autoconfigured for you to use OpenAI.

```java
@RestController
class ChatController {
    private final ChatModel chatModel;

    ChatController(ChatModel chatModel) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    String chat(@RequestParam(defaultValue = "What did Gandalf say to the Balrog?") String message) {
        return chatModel.call(message);
    }
}
```

## Running the application

The application relies on an OpenAI API for providing LLMs.

### When using OpenAI

First, make sure you have an [OpenAI account](https://platform.openai.com/signup).
Then, define an environment variable with the OpenAI API Key associated to your OpenAI account as the value.

```shell
export SPRING_AI_OPENAI_API_KEY=<INSERT KEY HERE>
```

Finally, run the Spring Boot application.

```shell
./gradlew bootRun
```

## Calling the application

You can now call the application that will use OpenAI and _gpt-4o_ to generate text based on a default prompt.
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

The next request is configured with Open AI-specific customizations.

```shell
http :8080/chat/openai-options message=="What can you see beyond what you can see? Give a short answer."
```
