package com.app.todo.scraper;

import javax.annotation.PostConstruct;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScraperConfig {

    @Bean
    public ChromeDriver driver() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        // For CI
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        // For local IDE
        // System.setProperty("webdriver.chrome.driver", "backend/todo/src/main/resources/chromedriver.exe"); // Windows
        // System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver"); // Mac

        chromeOptions.addArguments("--headless");
        return new ChromeDriver(chromeOptions);
    }
}
