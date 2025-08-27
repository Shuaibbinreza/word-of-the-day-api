package com.wordoftheday.api.work_of_the_day_api.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WrodApiService {

    private final WebClient randomWordClient;
    private final WebClient dictionaryClient;
    private final Cache<String, Object> cache;

    public WrodApiService(WebClient.Builder webClientBuilder,
                          @Value("${external.api.base-url}") String randomWordApiUrl,
                          @Value("${external.dictionary-api.base-url}") String dictionaryApiUrl) {
        this.randomWordClient = webClientBuilder.baseUrl(randomWordApiUrl).build();
        this.dictionaryClient = webClientBuilder.baseUrl(dictionaryApiUrl).build();

        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .maximumSize(10)
                .build();
    }

    // Fetch random word once
    private String fetchRandomWord() {
        System.out.println("Fetching random word from API");
        return randomWordClient.get()
                .uri(uriBuilder -> uriBuilder.path("/word").queryParam("number", 1).build())
                .retrieve()
                .bodyToMono(String[].class)
                .map(words -> words[0])
                .block();
    }

    // Fetch dictionary definition once
    private Object fetchWordDefinition(String word) {
        return dictionaryClient.get()
                .uri("/en/{word}", word)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    // Thread-safe method returning cached or fresh response
    public Object getRandomWordWithDefinition() {
        String cacheKey = "wordOfTheDay";

        Object result;
        synchronized (this) {
            Object cached = cache.getIfPresent(cacheKey);
            if (cached != null) {
                result = cached;
            } else {
                String word = fetchRandomWord();
                Object definition = fetchWordDefinition(word);

                result = Map.of("word", word, "definition", definition);
                cache.put(cacheKey, result);
            }
        }
        return result;
    }

    // Manually clear the cache
    public void clearWordOfTheDayCache() {
        cache.invalidate("wordOfTheDay");
    }
}