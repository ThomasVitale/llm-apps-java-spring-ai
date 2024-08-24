# Chat Models: OpenAI

Text generation with LLMs via OpenAI.

## Description

Spring AI provides a `ChatModel` low-level abstraction for integrating with LLMs via several providers, including OpenAI.

When using the _Spring AI OpenAI Spring Boot Starter_, a `ChatModel` object is autoconfigured for you to use OpenAI.

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

You can now call the application that will use OpenAI to generate text based on a default prompt.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/chat
```

Try passing your custom prompt and check the result.

```shell
http :8080/chat question=="What is the capital of Italy?"
```

The next request is configured with a custom temperature value to obtain a more creative, yet less precise answer.

```shell
http :8080/chat/generic-options question=="Why is a raven like a writing desk? Give a short answer."
```

The next request is configured with Open AI-specific customizations.

```shell
http :8080/chat/provider-options question=="What can you see beyond what you can see? Give a short answer."
```

The final request returns the model's answer as a stream.

```shell
http --stream :8080/chat/stream question=="Why is a raven like a writing desk? Answer in 3 paragraphs."
```
