# Chat Models: OpenAI

Chat completion with LLMs via OpenAI.

## Description

Spring AI provides a `ChatModel` low-level abstraction for integrating with LLMs via several providers, including OpenAI.

When using the _Spring AI OpenAI Spring Boot Starter_, a `ChatModel` object is autoconfigured for you to use OpenAI.

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

## OpenAI

The application consumes models from the [OpenAI](https://openai.com) platform.

### Create an account

Visit [platform.openai.com](https://platform.openai.com) and sign up for a new account.

### Configure API Key

In the OpenAI console, navigate to _Dashboard > API Keys_ and generate a new API key.
Copy and securely store your API key on your machine as an environment variable.
The application will use it to access the OpenAI API.

```shell
export OPENAI_API_KEY=<YOUR-API-KEY>
```

## Running the application

Run the application.

```shell
./gradlew bootRun
```

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
