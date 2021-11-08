package com.app.todo.scrapper;

//import com.app.todo.newsfunnel.Tag;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
//@AllArgsConstructor
public class ScrapperService {

    private final ChromeDriver driver;
    private static Map<String, List<String>> tagMap = new HashMap<>();

    static {
        String[] fnb = new String[] {"restaurants", "F&B, f&b"};
        tagMap.put("F&B", Arrays.asList(fnb));

        String[] tourism = new String[] {"tourists", "travel"};
        tagMap.put("Tourism", Arrays.asList(tourism));

        String[] healthcare = new String[] {"hospital"};
        tagMap.put("Healthcare", Arrays.asList(healthcare));
    }

    @Autowired
    public ScrapperService(ChromeDriver driver) {
        this.driver = driver;
    }

    public List<String> scrapeFAQ(String URL) {
        List<String> scrappedSrc = new ArrayList<>();
        driver.get(URL);

        final List<WebElement> tagList = driver.findElementsByTagName("img");

        for (WebElement element:tagList) {
            String src = element.getAttribute("src").toString();
            if (src.contains("safe-distance")) {
                scrappedSrc.add(src);
            }
        }

//        driver.quit();
        return scrappedSrc;
    }

    public void scrapeArticle(String articleURL, List<String> tag) {
        driver.get(articleURL);

        final List<WebElement> tagList = driver.findElementsByTagName("p");

        for (WebElement element:tagList) {
            String paraContent = element.getText();
            for (String key:tagMap.keySet()) {
                List<String> values = tagMap.get(key);
                for (String value : values) {
                    if (paraContent.contains(value)) {
                        if (!tag.contains(key)) {
                            tag.add(key);
                        }
//                        System.out.println("Tag is:" + key);
                        break;
                    }
                }
            }
        }


//        driver.quit();
    }
}
