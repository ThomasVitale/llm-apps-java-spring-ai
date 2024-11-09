# Structured Output: OpenAI

Converting the LLM output to structured Java objects via OpenAI.

## OpenAI

The application relies on the OpenAI API for providing LLMs.

### Create an OpenAI account

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
http :8080/chat/bean genre="rock" instrument="piano" -b
```

```shell
http POST :8080/chat/map -b
```

```shell
http :8080/chat/list genre="rock" instrument="piano" -b
```

OpenAI has also a native structured output feature, available through the Spring AI `ChatModel` API.

```shell
http :8080/model/chat/json genre="rock" instrument="piano" -b
```
