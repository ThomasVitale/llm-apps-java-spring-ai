# Output Parsers: OpenAI

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
http :8080/ai/chat/bean genre="rock" instrument="piano"
```

```shell
http :8080/ai/chat/map genre="rock" instrument="piano"
```

```shell
http :8080/ai/chat/list genre="rock" instrument="piano"
```
