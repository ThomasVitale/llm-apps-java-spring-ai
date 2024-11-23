# Prompts Templates: OpenAI

Prompting using templates with LLMs via OpenAI.

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
http :8080/chat/user genre="rock" instrument="piano" -b --pretty none
```

```shell
http --raw "What is the capital of Italy?" :8080/chat/system -b --pretty none
```

```shell
http --raw "What is the capital of Italy?" :8080/chat/external -b --pretty none
```
