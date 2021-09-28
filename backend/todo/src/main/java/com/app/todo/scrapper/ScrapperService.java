package com.app.todo.scrapper;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
//@AllArgsConstructor
public class ScrapperService {
    private static final String URL = "https://www.enterprisesg.gov.sg/media-centre/media-releases/2021/july/mr04821_updated-advisory-for-safe-management-measures-at-food-beverage-establishments";

    private final ChromeDriver driver;

    @Autowired
    public ScrapperService(ChromeDriver driver) {
        this.driver = driver;
    }

    public List<String> scrape() {
        List<String> scrappedSrc = new ArrayList<>();
        driver.get(URL);
        final WebElement words = driver.findElementByClassName("article-body");
        final List<WebElement> wordList = words.findElements(By.tagName("a"));
        wordList.forEach(word -> scrappedSrc.add(word.getAttribute("href").toString()));
        driver.quit();
        return scrappedSrc;
    }
}
