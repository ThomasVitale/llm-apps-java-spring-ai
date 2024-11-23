# Function Calling (Tools): Mistral AI

Function calling via Mistral AI.

## Mistral AI

The application consumes models from the [Mistral AI](https://mistral.ai) platform.

### Create an account

Visit [console.mistral.ai](https://console.mistral.ai) and sign up for a new account.
You can choose the "Experiment" plan, which gives you access to the Mistral APIs for free.

### Configure API Key

In the Mistral AI console, navigate to _API Keys_ and generate a new API key.
Copy and securely store your API key on your machine as an environment variable.
The application will use it to access the Mistral AI API.

```shell
export MISTRALAI_API_KEY=<YOUR-API-KEY>
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
http :8080/chat/function authorName=="J.R.R. Tolkien" -b
```

Try again. This time, the function calling strategy is configured in the call at runtime.

```shell
http :8080/chat/function/explicit authorName=="Philip Pullman" -b
```
