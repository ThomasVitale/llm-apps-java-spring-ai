# Multimodality: OpenAi

Multimodality with LLMs via OpenAI.

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

You can now call the application that will use OpenAI to generate text based on a default image.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/chat/image/file -b
```

Try passing your custom prompt and check the result.

```shell
http :8080/chat/image/file message=="Is there an animal in the picture?" -b
```

The image can also be fetched from a URL.

```shell
http :8080/chat/image/url message=="What's in the picture? Answer in one sentence" -b
```
