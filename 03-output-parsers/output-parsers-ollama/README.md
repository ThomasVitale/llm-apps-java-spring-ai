# Output Parsers: Ollama

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
http :8080/ai/chat/bean genre="rock" instrument="piano"
```

```shell
http :8080/ai/chat/map genre="rock" instrument="piano"
```

```shell
http :8080/ai/chat/list genre="rock" instrument="piano"
```
