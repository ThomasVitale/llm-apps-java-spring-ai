# Prompts Templates: OpenAI

```shell
export SPRING_AI_OPENAI_API_KEY=<INSERT KEY HERE>
```

```shell
./gradlew bootTestRun
```

```shell
http :8080/ai/chat/user genre="rock" instrument="piano"
```

```shell
http --raw "What is the capital of Italy?" :8080/ai/chat/system
```

```shell
http --raw "What is the capital of Italy?" :8080/ai/chat/external
```
