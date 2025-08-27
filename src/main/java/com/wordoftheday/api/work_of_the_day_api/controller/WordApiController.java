package com.wordoftheday.api.work_of_the_day_api.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wordoftheday.api.work_of_the_day_api.service.WrodApiService;

@RestController
public class WordApiController {

    private final WrodApiService apiService;

    public WordApiController(WrodApiService apiService) {
        this.apiService = apiService;
    }

    // Get word of the day (cached or fresh)
    @GetMapping("/word-of-the-day")
    public Object getWordOfTheDay() {
        return apiService.getRandomWordWithDefinition();
    }

    // Manually refresh cache
    @GetMapping("/word-of-the-day/refresh")
    public Object refreshWordOfTheDay() {
        apiService.clearWordOfTheDayCache();
        return apiService.getRandomWordWithDefinition();
    }
}

