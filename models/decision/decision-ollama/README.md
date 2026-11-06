# Decision Models: Ollama

Typed probabilistic decisions with decision models via Ollama.

## Description

Spring AI provides a `DecisionModel` abstraction for integrating with decision models via several providers, including Ollama.

Instead of generating text, a decision model answers typed questions about an input. Each answer carries the probabilities assigned by the model, so that your application decides what to do with them. Many tasks that are often implemented with a chat model and a structured output fit this model better, such as classifying a text, detecting a property, or checking a reply.

When using the _Spring AI Ollama Spring Boot Starter_, a `DecisionModel` object is autoconfigured for you to use Ollama.

This example is the customer support desk of an online musical instrument store.

| Endpoint                | What it decides                                                   | Feature                                   |
|-------------------------|-------------------------------------------------------------------|-------------------------------------------|
| `GET /tickets/on-topic`   | Is this message something the support desk should handle?        | `YesNoQuestion`                           |
| `GET /tickets/on-topic/threshold` | The same, with your own threshold                        | `YesNoQuestion` with a custom threshold   |
| `GET /tickets/category`   | Which team should handle this ticket?                            | `ChoiceQuestion`                          |
| `GET /tickets/sentiment`  | How does the customer feel?                                      | `ScoreQuestion`                           |
| `GET /tickets/triage`     | Where should this ticket go, and how urgent is it?               | Multiple questions about named fields     |
| `POST /support/chat`      | Can this message reach the chat model? Was the question answered? | Guardrails, conversation input            |

### Questions and answers

| Question         | Answer         | What you get                                                                                                                    |
|------------------|----------------|---------------------------------------------------------------------------------------------------------------------------------|
| `YesNoQuestion`  | `YesNoAnswer`  | The probability of "yes", and `isYes()` or `isYes(threshold)` to turn it into a decision.                                       |
| `ChoiceQuestion` | `ChoiceAnswer` | The selected option, the probability of each option, and the options ranked from the most to the least likely.                  |
| `ScoreQuestion`  | `ScoreAnswer`  | The expected level as a decimal `value`, the probability of each level, and `getNearestLevel()` for the level the model favors. |

Levels are numbered by position, starting from 0.

### Inputs

A decision can be about a text, a set of named fields, or a conversation. The Ollama decision models work on text: an input carrying media, such as an image, is rejected.

## Ollama

The application consumes models from an [Ollama](https://ollama.ai) inference server. If you don't have an Ollama server running locally on your laptop, an Ollama service will be automatically spun up for you by the [Arconia Dev Services](https://docs.arconia.io/arconia/latest/dev-services/) when you run the application. Either way, Spring AI will take care of pulling the needed Ollama models when the application starts, if they are not available yet on your machine.

The application uses `d1-omni:600m` as the decision model, and `granite4.2:3b` as the chat model behind the guardrails.

## Running the application

Run the application as follows:

```shell
./gradlew bootRun
```

Under the hood, in case no native Ollama connection is detected on your machine, the Arconia framework will automatically spin up an [Ollama](https://arconia.io/docs/arconia/latest/dev-services/ollama/) inference service using Testcontainers (see [Arconia Dev Services](https://arconia.io/docs/arconia/latest/dev-services/) for more information).

The application will be accessible at http://localhost:8080.

## Calling the application

> [!NOTE]
> These examples use the [httpie](https://httpie.io) CLI to send HTTP requests.

### Yes/No Question

A `YesNoQuestion` fits any check where your code takes one of two paths. Here, the support desk checks whether a message is within its scope.

```shell
http :8080/tickets/on-topic text=="My order 48213 was supposed to arrive last week and the tracking page hasn't changed since Monday." -b
```

```json
{
  "probability": 0.9549885076975951,
  "yes": true
}
```

```shell
http :8080/tickets/on-topic text=="What's the recipe for lasagna?" -b
```

```json
{
  "probability": 0.040306487699081406,
  "yes": false
}
```

A message can be about music without being something the store can help with.

```shell
http :8080/tickets/on-topic text=="Who composed the Moonlight Sonata?" -b
```

```json
{
  "probability": 0.31994518986598025,
  "yes": false
}
```

The model only gives a probability. Your application chooses how sure it needs to be, depending on what a wrong decision costs. With a higher threshold, the same answer can result in a different decision.

```shell
http :8080/tickets/on-topic/threshold text=="Which digital piano is best for a beginner?" threshold==0.9 -b
```

```shell
false
```

### Choice Question

A `ChoiceQuestion` selects one option from a defined set, which makes it a good fit for classification. The ranked options and their probabilities tell you how clear the case is: when the top two options are close, a person may handle the ticket better.

```shell
http :8080/tickets/category text=="The neck of my new guitar is cracked. Can I get my money back?" -b
```

```json
{
  "confidence": 0.9147619382709841,
  "probabilities": {
    "account": 0.0002730349351192351,
    "billing": 0.009026164817320066,
    "other": 0.032416890338795926,
    "returns": 0.9289682818924867,
    "shipping": 0.0003463363002087557,
    "technical": 0.028969291716069313
  },
  "probability": 0.9289682818924867,
  "rankedOptions": [
    "returns",
    "other",
    "technical",
    "billing",
    "shipping",
    "account"
  ],
  "value": "returns"
}
```

### Score Question

A `ScoreQuestion` selects a level on an ordered scale. Here, the sentiments go from the most negative (`angry`, level 0) to the most positive (`happy`, level 3).

```shell
http :8080/tickets/sentiment text=="This is the second time my delivery is late. I'm disappointed, please look into it." -b
```

```json
{
  "confidence": 0.7762714132000147,
  "nearestLevel": 1,
  "probabilities": [
    0.08997499567491726,
    0.7770193741145547,
    0.13225766929598778,
    0.0007479609145401404
  ],
  "roundedLevel": 1,
  "value": 1.0437785954501506
}
```

The `nearestLevel` is the level the model finds most likely, which in this case corresponds to `frustrated`. The `value` is the average level weighted by the probabilities, which is useful for tracking trends or sorting tickets.

### Multiple Questions

A single call can answer several questions of different types. Here, the input is a set of named fields rather than a free text, and the application combines the answers into a routing decision: tickets from angry customers are escalated, and everything else goes to the team in charge of the category.

```shell
http :8080/tickets/triage \
  subject=="Duplicate charge, cancelling" \
  body=="You charged our music school twice for order 48213 and nobody replies. We are closing our account and moving to another supplier." \
  customer==music-school -b
```

```json
{
  "category": "billing",
  "queue": "escalated",
  "sentiment": "angry",
  "urgency": "high"
}
```

```shell
http :8080/tickets/triage \
  subject=="Amplifier cuts out" \
  body=="My new guitar amplifier keeps cutting out every few minutes. How can I fix it?" -b
```

```json
{
  "category": "technical",
  "queue": "technical",
  "sentiment": "frustrated",
  "urgency": "normal"
}
```

### Guardrails

The `/support/chat` endpoint answers questions using a chat model and the store policy. Two [advisors](src/main/java/com/thomasvitale/ai/spring/guardrails) use the decision model to guard it.

The `DecisionInputGuardrailAdvisor` runs before the chat model. It asks two questions about the user message at once: whether it contains sensitive data, and whether it is on-topic. If the message fails either check, the chat model isn't called at all.

```shell
http --raw "What's the recipe for lasagna?" :8080/support/chat -b
```

```text
I can only help with questions about our store, your orders, and our instruments.
```

```shell
http --raw "Please refund order 48213 to my credit card 4111 1111 1111 1111, CVV 123." :8080/support/chat -b
```

```text
For your safety, please don't share payment details or personal ID numbers in this chat. Remove them and ask your question again.
```

The `DecisionOutputGuardrailAdvisor` runs after the chat model. It passes the conversation (a `List<Message>`) to the decision model to determine whether the user's question was answered. If not, it replaces the reply with a handoff to a human agent.

```shell
http --raw "How long do I have to return an instrument?" :8080/support/chat -b
```

```text
You have 30 days from the date of delivery to return an instrument, provided it is returned in its original packaging.
```

The store policy doesn't cover the next question, so the chat model replies that it doesn't have that information, and the output guardrail hands the question off.

```shell
http --raw "Do you ship to Iceland?" :8080/support/chat -b
```

```text
I'm sorry, I couldn't find an answer to your question. I'm passing it on to one of our support agents, who will get back to you.
```
