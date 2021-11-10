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

        // For CI
        // System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        // For local IDE
        // System.setProperty("webdriver.chrome.driver", "backend/todo/src/main/resources/chromedriver.exe"); // Windows
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver"); // Mac

        // For deployment
        // System.setProperty("GOOGLE_CHROME_BIN", "/app/.apt/usr/bin/google-chrome");
        // System.setProperty("CHROMEDRIVER_PATH", "/app/.chromedriver/bin/chromedriver");
    }

    @Bean
    public ChromeDriver driver() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        // For both local and Deploy
        chromeOptions.addArguments("--headless");

        // For Deploy ONLY
//        chromeOptions.setBinary("/app/.apt/usr/bin/google-chrome");
//        chromeOptions.addArguments("--enable-javascript");
//        chromeOptions.addArguments("--disable-gpu");
//        chromeOptions.addArguments("--no-sandbox");
        return new ChromeDriver(chromeOptions);
    }
}
