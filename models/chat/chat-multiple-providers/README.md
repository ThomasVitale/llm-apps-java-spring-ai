# Chat Models: Multiple Providers

Chat completion with LLMs via multiple providers.

## Description

This example shows how to use both OpenAI and Mistral AI in the same application.

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

## Mistral AI

The application consumes models from the [Mistral AI](https://mistral.ai) platform.

### Create an account

Visit [console.mistral.ai](https://console.mistral.ai) and sign up for a new account.
You can choose the "Experiment" plan, which gives you access to the Mistral APIs for free.

### Configure API Key

In the Mistral AI console, navigate to _API Keys_ and generate a new API key.
Copy and securely store your API key on your machine as an environment variable.
The application will use it to access the Mistral AI API.

```shell
export MISTRALAI_API_KEY=<YOUR-API-KEY>
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
