# Prompts Basic: OpenAI

Prompting using simple text with LLMs via OpenAI.

## Running the application

The application relies on an OpenAI API for providing LLMs.

### When using OpenAI

First, make sure you have an OpenAI account.
Then, define an environment variable with the OpenAI API Key associated to your OpenAI account as the value.

```shell
export SPRING_AI_OPENAI_API_KEY=<INSERT KEY HERE>
```

Finally, run the Spring Boot application.

```shell
./gradlew bootRun
```

## Calling the application

You can now call the application that will use OpenAI and _gpt-3.5-turbo_ to generate an answer to your questions.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http --raw "What is the capital of Italy?" :8080/ai/chat/simple
```

```shell
http --raw "What is the capital of Italy?" :8080/ai/chat/prompt
```

```shell
http --raw "What is the capital of Italy?" :8080/ai/chat/full
```
