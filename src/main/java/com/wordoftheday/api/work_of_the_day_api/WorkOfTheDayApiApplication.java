package com.wordoftheday.api.work_of_the_day_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WorkOfTheDayApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkOfTheDayApiApplication.class, args);
	}

}
