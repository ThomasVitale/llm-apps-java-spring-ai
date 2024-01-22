# Text Document Readers: Ollama

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
http --raw "What is Iorek's biggest dream?" :8080/ai/doc/chat
```

```shell
http --raw "Who is Lucio?" :8080/ai/doc/chat
```
