package com.wordoftheday.api.work_of_the_day_api.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wordoftheday.api.work_of_the_day_api.service.WrodApiService;

@RestController
public class WordApiController {
    private final WrodApiService apiService;

    public WordApiController(WrodApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/random-words")
    public List<String> getRandomWords() {
        return Collections.singletonList(apiService.getRandomWord());
    }

    @GetMapping("/word-of-the-day")
    public Object getWordOfTheDay() {
        return apiService.getRandomWordWithDefinition();
    }
}
