# Tools / Function Calling: Mistral AI

Tools / Function calling via Mistral AI.

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

Try the method-based tool calling strategy.

```shell
http :8080/chat/method/no-args -b
```

```shell
http :8080/chat/method/void user=="Jessica" -b
```

```shell
http :8080/chat/method/single authorName=="J.R.R. Tolkien" -b
```

```shell
http :8080/chat/method/single authorName=="C.S. Lewis" -b
```

```shell
http :8080/chat/method/list bookTitle1=="Narnia" bookTitle2=="The Hobbit" -b
```

Next, try the function-based tool calling strategy.

```shell
http :8080/chat/function/void-input -b
```

```shell
http :8080/chat/function/void-input/callback -b
```

```shell
http :8080/chat/function/void-output user=="Jessica" -b
```

```shell
http :8080/chat/function/void-output/callback user=="Jessica" -b
```

```shell
http :8080/chat/function/single authorName=="J.R.R. Tolkien" -b
```

```shell
http :8080/chat/function/single/callback authorName=="J.R.R. Tolkien" -b
```

```shell
http :8080/chat/function/list bookTitle1=="Narnia" bookTitle2=="The Hobbit" -b
```

```shell
http :8080/chat/function/list/callback bookTitle1=="Narnia" bookTitle2=="The Hobbit" -b
```
