# Chat Models: Multiple Providers

Text generation with LLMs via multiple providers.

## Description

This example shows how to use both OpenAI and Mistral AI in the same application.

## Running the application

The application relies on OpenAI API and Mistral AI API for providing LLMs.

First, make sure you have an [OpenAI account](https://platform.openai.com/signup).
Then, define an environment variable with the OpenAI API Key associated to your OpenAI account as the value.

```shell
export SPRING_AI_OPENAI_API_KEY=<INSERT KEY HERE>
```

You also need a [Mistral AI account](https://console.mistral.ai).
Then, define an environment variable with the Mistral AI API Key associated to your Mistral AI account as the value.

```shell
export SPRING_AI_MISTRALAI_API_KEY=<INSERT KEY HERE>
```

Finally, run the Spring Boot application.

```shell
./gradlew bootRun
```

## Calling the application

You can now call the application that will use either OpenAI or Mistral AI to generate text based on a default prompt.
This example uses [httpie](https://httpie.io) to send HTTP requests.

Using OpenAI:

```shell
http :8080/chat/openai question=="What is the capital of Italy?" -b
```

Using Mistral AI:

```shell
http :8080/chat/mistral-ai question=="What is the capital of Italy?" -b
```

The next request is configured with OpenAI-specific customizations.

```shell
http :8080/chat/openai-options question=="Why is a raven like a writing desk? Give a short answer." -b
```

The next request is configured with Mistral AI-specific customizations.

```shell
http :8080/chat/mistral-ai-options question=="Why is a raven like a writing desk? Give a short answer." -b
```
