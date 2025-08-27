package com.wordoftheday.api.work_of_the_day_api.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DictionaryEntry(
        List<Meaning> meanings
) {}
