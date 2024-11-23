# Multimodality: OpenAi

Multimodality with LLMs via OpenAI.

## OpenAI

The application consumes models from the [OpenAI](https://openai.com) platform.

### Create an account

Visit [platform.openai.com](https://platform.openai.com) and sign up for a new account.

### Configure API Key

In the OpenAI console, navigate to _Dashboard > API Keys_ and generate a new API key.
Copy and securely store your API key on your machine as an environment variable.
The application will use it to access the OpenAI API.

```shell
export OPENAI_API_KEY=<YOUR-API-KEY>
```

## Running the application

Run the application.

```shell
./gradlew bootRun
```

## Calling the application

> [!NOTE]
> These examples use the [httpie](https://httpie.io) CLI to send HTTP requests.

Call the application that will use a chat model to answer your question.

```shell
http :8080/chat/image/file question=="What do you see in this picture? Give a short answer" -b
```

Try passing your custom prompt and check the result.

```shell
http :8080/chat/image/file question=="Is there an animal in the picture?" -b
```

The image can also be fetched from a URL.

```shell
http :8080/chat/image/url question=="What's in the picture? Answer in one sentence" -b
```
