# Prompts Templates: Ollama

## Running the application

### When using Ollama

```shell
ollama run llama2
```

```shell
./gradlew bootTestRun
```

### When using Docker/Podman

```shell
./gradlew bootTestRun
```

## Calling the application

```shell
http :8080/ai/chat/user genre="rock" instrument="piano"
```

```shell
http --raw "What is the capital of Italy?" :8080/ai/chat/system
```

```shell
http --raw "What is the capital of Italy?" :8080/ai/chat/external
```
