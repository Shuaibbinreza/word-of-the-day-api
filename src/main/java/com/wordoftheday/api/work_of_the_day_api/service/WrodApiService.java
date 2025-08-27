package com.wordoftheday.api.work_of_the_day_api.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WrodApiService {
    private final WebClient randomWordClient;
    private final WebClient dictionaryClient;

    public WrodApiService(WebClient.Builder webClientBuilder,
                          @Value("${external.api.base-url}") String randomWordApiUrl,
                          @Value("${external.dictionary-api.base-url}") String dictionaryApiUrl) {
        this.randomWordClient = webClientBuilder.baseUrl(randomWordApiUrl).build();
        this.dictionaryClient = webClientBuilder.baseUrl(dictionaryApiUrl).build();
    }

    /**
     * Call Random Word API and return a single word
     */
    public String getRandomWord() {
        return randomWordClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/word")
                        .queryParam("number", 1)
                        .build())
                .retrieve()
                .bodyToMono(String[].class)
                .map(words -> words[0]) // get the first word
                .block();
    }

    /**
     * Call Dictionary API for a single word
     */
    public Object getWordDefinition(String word) {
        return dictionaryClient.get()
                .uri("/en/{word}", word)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    /**
     * Example: Combine both APIs
     */
    public Object getRandomWordWithDefinition() {
        String word = getRandomWord();

        return Map.of(
                "word", word,
                "definition", getWordDefinition(word)
        );
    }
}
