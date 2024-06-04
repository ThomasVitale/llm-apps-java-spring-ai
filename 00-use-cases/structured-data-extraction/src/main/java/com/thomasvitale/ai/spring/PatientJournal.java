package com.thomasvitale.ai.spring;

import java.util.List;

public record PatientJournal(String fullName, List<Observation> observations, Diagnosis diagnosis) {
    public record Observation(String type, String content) {}
    public record Diagnosis(String content) {}
}
