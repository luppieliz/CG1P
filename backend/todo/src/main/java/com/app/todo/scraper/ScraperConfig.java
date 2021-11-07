package com.app.todo.scraper;

import javax.annotation.PostConstruct;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScraperConfig {
    @PostConstruct
    void postConstruct() {
        /*
         * Path has to be set this way for CI. If running on VSCode without the Spring
         * Boot extension pack, add `backend/todo` in front of the path.
         */
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe"); // Windows
        //System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver"); // Mac
    }

    @Bean
    public ChromeDriver driver() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        return new ChromeDriver(chromeOptions);
    }
}