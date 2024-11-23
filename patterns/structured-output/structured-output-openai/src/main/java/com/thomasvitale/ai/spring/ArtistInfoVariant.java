package com.thomasvitale.ai.spring;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ArtistInfoVariant(@JsonProperty(required = true) String name, @JsonProperty(required = true) String band) {}
