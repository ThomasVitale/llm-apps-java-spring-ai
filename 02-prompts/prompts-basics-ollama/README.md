# Prompts Basic: Ollama

## Running the application

### When using Ollama

```shell
ollama run llama2
```

```shell
./gradlew bootRun
```

### When using Docker/Podman

```shell
./gradlew bootTestRun
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
