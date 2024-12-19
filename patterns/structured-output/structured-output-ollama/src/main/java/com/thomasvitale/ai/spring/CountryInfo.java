package com.thomasvitale.ai.spring;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CountryInfo(
        @JsonProperty(required = true) String name,
        @JsonProperty(required = true) String capital,
        @JsonProperty(required = true) List<String> languages
) {}
