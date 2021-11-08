package com.app.todo.scraper;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScraperService {

    private final ChromeDriver driver;
    private static Map<String, Set<String>> tagMap = new HashMap<>();

    static {
        String[] fnb = new String[] {"restaurants", "F&B, f&b"};
        tagMap.put("F&B", new HashSet<String>(Arrays.asList(fnb)));

        String[] tourism = new String[] {"tourists", "travel"};
        tagMap.put("Tourism", new HashSet<String>(Arrays.asList(tourism)));

        String[] healthcare = new String[] {"hospital"};
        tagMap.put("Healthcare", new HashSet<String>(Arrays.asList(healthcare)));

        String[] retail = new String[] {"stores", "retail", "shopping"};
        tagMap.put("Retail", new HashSet<>(Arrays.asList(retail)));
    }

    @Autowired
    public ScraperService(ChromeDriver driver) {
        this.driver = driver;
    }

    /**
     * Find all the sources of info-graphics.
     * @param faqURL
     * @return A list of scrapped links for the info-graphics.
     */
    public List<String> scrapeFAQ(final String faqURL) {
        List<String> scrappedSrc = new ArrayList<>();
        final String TOPIC_KEYWORD = "safe-distance";

        driver.get(faqURL);

        // Find images with tag "img"
        final List<WebElement> tagList = driver.findElementsByTagName("img");

        // Find all the sources of safe-distance info-graphics
        for (WebElement element:tagList) {
            String src = element.getAttribute("src").toString();
            if (src.contains(TOPIC_KEYWORD)) {
                scrappedSrc.add(src);
            }
        }

        return scrappedSrc;
    }

    /**
     * Find all the related tags for a given article.
     * @param articleURL
     */
    public List<String> scrapeArticleForTags(final String articleURL) {
        driver.get(articleURL);
        Set<String> tempTagSet = new HashSet<>();

        final List<WebElement> tagList = driver.findElementsByTagName("p");

        // Searching through the article's content to find suitable tags
        for (WebElement element:tagList) {
            String paraContent = element.getText();

            for (String keyTag : tagMap.keySet()) {
                Set<String> tagWords = tagMap.get(keyTag);

                for (String value : tagWords) {
                    if (paraContent.contains(value)) {
                        tempTagSet.add(keyTag);
                    }
                }
            }
        }

        return new ArrayList<>(tempTagSet);
    }
}
