package com.app.todo.scraper;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ScraperService {

    @Autowired
    private ScraperConfig scraperConfig;

    private static Map<String, Set<String>> tagMap = new HashMap<>();
    private static Map<String, String> keywordMap = new HashMap<>(); //<keyword, tag>
    static {
        String[] fnb = new String[] {"restaurants", "F&B, f&b", "eating", "dining", "drinks"};
        tagMap.put("F&B", new HashSet<String>(Arrays.asList(fnb)));

        String[] tourism = new String[] {"tourists", "travel"};
        tagMap.put("Tourism", new HashSet<String>(Arrays.asList(tourism)));

        String[] healthcare = new String[] {"hospital"};
        tagMap.put("Healthcare", new HashSet<String>(Arrays.asList(healthcare)));

        String[] retail = new String[] {"stores", "retail", "shopping"};
        tagMap.put("Retail", new HashSet<>(Arrays.asList(retail)));

        //new implementation
        for (String keyword : fnb) {
            keywordMap.put(keyword, "F&B");
        }
        for (String keyword : tourism) {
            keywordMap.put(keyword, "Tourism");
        }
        for (String keyword : healthcare) {
            keywordMap.put(keyword, "Healthcare");
        }
        for (String keyword : retail) {
            keywordMap.put(keyword, "Retail");
        }
    }



    /**
     * Find all the sources of info-graphics.
     * @param faqURL
     * @return A list of scrapped links for the info-graphics.
     */
    public List<String> scrapeFAQ(final String faqURL) {
        List<String> scrappedSrc = new ArrayList<>();
        final String TOPIC_KEYWORD = "safe-distance";

        ChromeDriver driver = setDriver();

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
        ChromeDriver driver = setDriver();

        driver.get(articleURL);
        Set<String> tempTagSet = new HashSet<>();

        final List<WebElement> tagList = driver.findElementsByTagName("p");
        /* new scraping
        * collect all words into a string, then split into a string array
        * convert all p elements into a map of word frequencies O(n): number of words
        *       check if keyword list has entries in word frequencies list m number of keywords at O(1) each
        * complexity: n^2
         */

        //process all the paragraphs into a list of strings of lowercase words without punctuation
        String allText = "";
        for (WebElement element:tagList) {
            String text = element.getText().replaceAll("\\p{Punct}", "").toLowerCase();
            allText += " " + text;
        }
        String [] words = allText.split(" ");

        //count all the words into a word freq map
        Map<String, Long> frequencyMap = Arrays.stream(words)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        //compare words with keyword map
        for (String keyword : keywordMap.keySet()) {
            if (frequencyMap.containsKey(keyword)) {
                tempTagSet.add(keyword);
            }
        }

        List<String> resultTag = new ArrayList<>();
        for (String keyword : tempTagSet) {
            resultTag.add(keywordMap.get(keyword));
        }

        return resultTag;
    }

    ChromeDriver setDriver() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        chromeOptions.addArguments("--headless");
        return new ChromeDriver(chromeOptions);
    }
}
