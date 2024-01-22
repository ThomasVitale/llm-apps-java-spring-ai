# Chat Models: OpenAI

## Running the application

### When using OpenAI

```shell
export SPRING_AI_OPENAI_API_KEY=<INSERT KEY HERE>
```

```shell
./gradlew bootRun
```

## Calling the application

```shell
http :8080/ai/chat
```

```shell
http :8080/ai/chat message=="What's the capital of Italy?"
```
