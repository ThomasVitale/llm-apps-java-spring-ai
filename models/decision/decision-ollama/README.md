# Decision Models: Ollama

Typed probabilistic decisions with decision models via Ollama.

## Description

Spring AI provides a `DecisionModel` abstraction for integrating with decision models via several providers, including Ollama.

Instead of generating text, a decision model answers typed questions about an input. Each answer carries the probabilities assigned by the model, so that your application decides what to do with them.

When using the _Spring AI Ollama Spring Boot Starter_, a `DecisionModel` object is autoconfigured for you to use Ollama.

```java
@Bean
CommandLineRunner decide(DecisionModel decisionModel) {
    return _ -> {
        var response = decisionModel.call(DecisionPrompt.builder()
                .text("A wizard stands on a narrow bridge. A demon made of flame, immune to fire, is about to cross it.")
                .question("spell", ChoiceQuestion.builder()
                        .text("Which spell fits the situation best?")
                        .option("barrier", "For when a monster must be stopped from crossing")
                        .option("fireball", "For when a monster is weak to fire")
                        .option("light", "For when it is too dark to see")
                        .option("summon_snacks", "For when hungry halflings are complaining")
                        .build())
                .question("danger", YesNoQuestion.builder()
                        .text("Is the wizard in mortal danger?")
                        .whenYes("A monster or an enemy is present and about to attack, burn or crush him")
                        .whenNo("No monster or enemy is around and nothing threatens him")
                        .build())
                .question("drama", ScoreQuestion.builder()
                        .text("How dramatic is this moment?")
                        .level("mundane", "Like doing the dishes after second breakfast")
                        .level("tense")
                        .level("legendary")
                        .build())
                .build());

        System.out.println("Spell: " + response.getChoice("spell").getValue());
        System.out.println("Probability of danger: " + response.getYesNo("danger").getProbability());
        System.out.println("Drama level: " + response.getScore("drama").getNearestLevel());
    };
}
```

### Questions and answers

| Question         | Answer        | What you get                                                                                                                  |
|------------------|---------------|-------------------------------------------------------------------------------------------------------------------------------|
| `YesNoQuestion`  | `YesNoAnswer` | The probability of "yes", and `isYes()` or `isYes(threshold)` to turn it into a decision.                                     |
| `ChoiceQuestion` | `ChoiceAnswer` | The selected option, the probability of each option, and the options ranked from the most to the least likely.                |
| `ScoreQuestion`  | `ScoreAnswer` | The expected level as a decimal `value`, the probability of each level, and `getNearestLevel()` for the level the model favors. |

Levels are numbered by position, starting from 0. A provider can also decline to answer a single question, which results in a `RefusalAnswer` for that question only.

### Inputs

A decision can be about a text, a set of named fields, or a conversation. The Ollama decision models work on text: an input carrying media, such as an image, is rejected.

## Ollama

The application consumes models from an [Ollama](https://ollama.ai) inference server. If you don't have an Ollama server running locally on your laptop, an Ollama service will be automatically spun up for you by the [Arconia Dev Services](https://docs.arconia.io/arconia/latest/dev-services/) when you run the application. Either way, Spring AI will take care of pulling the needed Ollama models when the application starts, if they are not available yet on your machine.

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

Call the application that will use a decision model to answer a choice, a yes/no, and a score question about a situation.

```shell
http :8080/decision situation=="A wizard stands on a narrow bridge. A demon made of flame, immune to fire, is about to cross it." -b
```

The response contains the answer to each question, by question name. The following is an abridged example.

```json
{
  "spell": {
    "value": "barrier",
    "probability": 0.92,
    "probabilities": { "barrier": 0.92, "fireball": 0.08, "light": 0.003, "summon_snacks": 0.0002 },
    "rankedOptions": ["barrier", "fireball", "light", "summon_snacks"],
    "confidence": 0.9
  },
  "danger": {
    "probability": 0.82,
    "yes": true
  },
  "drama": {
    "value": 1.27,
    "nearestLevel": 1,
    "roundedLevel": 1,
    "probabilities": [0.0005, 0.73, 0.27],
    "confidence": 0.59
  }
}
```

The next request lets the application act on the probability of "yes", using a custom threshold. It defaults to 0.7.

```shell
http :8080/decision/threshold situation=="The wizard sits by the fire in a cosy inn, enjoying a pipe. Nobody is around and nothing threatens him." -b
```

```shell
http :8080/decision/threshold situation=="A wizard stands on a narrow bridge. A demon made of flame, immune to fire, is about to cross it." threshold==0.9 -b
```

The next request makes a decision about named fields rather than free text.

```shell
http :8080/decision/fields traveller==Frodo destination=="Mount Doom" companions==1 -b
```

The next request makes a decision about a conversation, judging a reply from an assistant.

```shell
http :8080/decision/conversation question=="Who forged the One Ring?" answer=="Stop bothering me with your silly questions." -b
```
