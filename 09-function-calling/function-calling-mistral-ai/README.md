# Function Calling: Mistral AI

Function calling via Mistral AI.

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

You can now call the application that will use Mistral AI and _mistral-small-latest_ to call functions in order to answer questions.
This example uses [httpie](https://httpie.io) to send HTTP requests.

```shell
http :8080/chat/function
```

Try passing your custom prompt and check the result.

```shell
http :8080/chat/function authorName=="Philip Pullman"
```
