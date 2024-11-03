# Image Models: OpenAI

Image generation with LLMs via OpenAI.

## Description

Spring AI provides an `ImageModel` abstraction for integrating with LLMs via several providers, including OpenAI.

When using the _Spring AI OpenAI Spring Boot Starter_, an `ImageModel` object is autoconfigured for you to use OpenAI.

```java
@Bean
CommandLineRunner chat(ImageModel imageModel) {
    return _ -> {
        var response = imageModel.call(new ImagePrompt(message)).
                getResult().getOutput().getUrl();
        System.out.println(response);
    };
}
```

## OpenAI

The application relies on the OpenAI API for providing LLMs.

### Create an OpenAI account

Visit [https://platform.openai.com](platform.openai.com) and sign up for a new account.

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

Call the application that will use an image model to generate an image.

```shell
http :8080/image message=="Here comes the sun"
```

The next request is configured with provider-specific customizations.

```shell
http :8080/image/provider-options message=="Here comes the sun"
```
