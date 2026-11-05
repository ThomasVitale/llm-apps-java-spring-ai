package com.thomasvitale.ai.spring;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.decision.DecisionModel;
import org.springframework.ai.decision.DecisionPrompt;
import org.springframework.ai.decision.answer.DecisionAnswer;
import org.springframework.ai.decision.question.ChoiceQuestion;
import org.springframework.ai.decision.question.ScoreQuestion;
import org.springframework.ai.decision.question.YesNoQuestion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
class DecisionController {

    private static final YesNoQuestion DANGER_QUESTION = YesNoQuestion.builder()
            .text("Is the wizard in mortal danger?")
            .whenYes("A monster or an enemy is present and about to attack, burn or crush him")
            .whenNo("No monster or enemy is around and nothing threatens him")
            .build();

    private final DecisionModel decisionModel;

    DecisionController(DecisionModel decisionModel) {
        this.decisionModel = decisionModel;
    }

    @GetMapping("/decision")
    Map<String, DecisionAnswer> decision(String situation) {
        return decisionModel.call(DecisionPrompt.builder()
                        .text(situation)
                        .question("spell", ChoiceQuestion.builder()
                                .text("Which spell fits the situation best?")
                                .option("barrier", "For when a monster must be stopped from crossing")
                                .option("fireball", "For when a monster is weak to fire")
                                .option("light", "For when it is too dark to see")
                                .option("summon_snacks", "For when hungry halflings are complaining")
                                .build())
                        .question("danger", DANGER_QUESTION)
                        .question("drama", ScoreQuestion.builder()
                                .text("How dramatic is this moment?")
                                .level("mundane", "Like doing the dishes after second breakfast")
                                .level("tense")
                                .level("legendary")
                                .build())
                        .build())
                .getAnswers();
    }

    @GetMapping("/decision/threshold")
    String decisionThreshold(String situation, @RequestParam(defaultValue = "0.7") double threshold) {
        var danger = decisionModel.call(situation, Map.of("danger", DANGER_QUESTION)).getYesNo("danger");
        return danger.isYes(threshold) ? "Run, you fools! Probability of danger: " + danger.getProbability()
                : "Keep calm and smoke some pipe-weed. Probability of danger: " + danger.getProbability();
    }

    @GetMapping("/decision/fields")
    Map<String, DecisionAnswer> decisionFields(String traveller, String destination, int companions) {
        var fields = new LinkedHashMap<String, Object>();
        fields.put("traveller", traveller);
        fields.put("destination", destination);
        fields.put("companions", companions);

        return decisionModel.call(DecisionPrompt.builder()
                        .fields(fields)
                        .question("risk", ScoreQuestion.of("How risky is this journey?", "low", "medium", "high"))
                        .question("escort", YesNoQuestion.builder()
                                .text("Does the traveller need an armed escort?")
                                .whenYes("The destination is dangerous and the traveller has few companions")
                                .whenNo("The destination is safe or the traveller is already well protected")
                                .build())
                        .build())
                .getAnswers();
    }

    @GetMapping("/decision/conversation")
    Map<String, DecisionAnswer> decisionConversation(String question, String answer) {
        return decisionModel.call(DecisionPrompt.builder()
                        .conversation(List.of(new UserMessage(question), AssistantMessage.builder().content(answer).build()))
                        .question("answered", YesNoQuestion.builder()
                                .text("Did the assistant answer the user's question?")
                                .whenYes("The assistant's reply gives the information the user asked for")
                                .whenNo("The assistant's reply dodges the question or refuses to answer it")
                                .build())
                        .question("tone", ChoiceQuestion.builder()
                                .text("What is the tone of the assistant's reply?")
                                .option("polite", "The reply is courteous or matter-of-fact")
                                .option("rude", "The reply insults, scolds or brushes off the user")
                                .build())
                        .build())
                .getAnswers();
    }

}
