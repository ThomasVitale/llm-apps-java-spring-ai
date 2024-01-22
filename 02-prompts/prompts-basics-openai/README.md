# Prompts Basic: OpenAI

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
http --raw "What is the capital of Italy?" :8080/ai/chat/simple
```

```shell
http --raw "What is the capital of Italy?" :8080/ai/chat/prompt
```

```shell
http --raw "What is the capital of Italy?" :8080/ai/chat/full
```
