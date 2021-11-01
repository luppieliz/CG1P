package com.app.todo.scrapper;

import javax.annotation.PostConstruct;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScrapperConfig {
    @PostConstruct
    void postConstruct() {
        // Remove .exe if using Mac
        System.setProperty("webdriver.chrome.driver", "backend/todo/src/main/java/com/app/todo/scrapper/chromedriver");
    }

    @Bean
    public ChromeDriver driver() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        return new ChromeDriver(chromeOptions);
    }
}