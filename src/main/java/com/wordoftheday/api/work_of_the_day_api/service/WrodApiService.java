package com.wordoftheday.api.work_of_the_day_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WrodApiService {
    private final WebClient webClient;

    public WrodApiService(WebClient.Builder webClientBuilder,
                      @Value("${external.api.base-url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public List<String> getRandomWords(int number) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/word")
                        .queryParam("number", number)
                        .build())
                .retrieve()
                .bodyToMono(String[].class)  
                .map(List::of)   
                .block();
    }
}
