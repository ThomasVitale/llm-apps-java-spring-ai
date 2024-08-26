# Structured Output: OpenAI

Converting the LLM output to structured Java objects via OpenAI.

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

You can now call the application that will use OpenAI to generate an answer to your questions.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/chat/bean genre="rock" instrument="piano" -b
```

```shell
http :8080/chat/map genre="rock" instrument="piano" -b
```

```shell
http :8080/chat/list genre="rock" instrument="piano" -b
```

OpenAI has also a native structured output feature, available through the Spring AI `ChatModel` API.

```shell
http :8080/model/chat/json genre="rock" instrument="piano" -b
```
