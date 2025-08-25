package com.wordoftheday.api.work_of_the_day_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wordoftheday.api.work_of_the_day_api.service.WrodApiService;

@RestController
public class ApiController {
    private final WrodApiService apiService;

    public ApiController(WrodApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/random-words")
    public List<String> getRandomWords(@RequestParam(defaultValue = "1") int number) {
        return apiService.getRandomWords(number);
    }
}
