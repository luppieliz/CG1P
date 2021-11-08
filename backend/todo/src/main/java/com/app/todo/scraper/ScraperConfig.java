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
//        System.setProperty("webdriver.chrome.driver", "backend/todo/src/main/resources/chromedriver.exe"); // Local Windows Repo
//        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver"); // Mac
<<<<<<< HEAD
        System.setProperty("webdriver.chrome.driver", "backend/todo/src/main/resources/chromedriver.exe"); // Windows
=======
          System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe"); // Windows
>>>>>>> 7954fa3d25a2158fe16ff36dab28d64edfd78b19
    }

    @Bean
    public ChromeDriver driver() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        return new ChromeDriver(chromeOptions);
    }
}
