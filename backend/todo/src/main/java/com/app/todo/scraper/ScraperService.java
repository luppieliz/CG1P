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

//    private static Map<String, Set<String>> tagMap = new HashMap<>();
    private static Map<String, String> keywordMap = new HashMap<>(); //<keyword, tag>
    static {
//        String[] fnb = new String[] {"restaurants", "F&B, ", "eating", "dining", "drinks"};
//        tagMap.put("F&B", new HashSet<String>(Arrays.asList(fnb)));
//
//        String[] tourism = new String[] {"tourists", "travel"};
//        tagMap.put("Tourism", new HashSet<String>(Arrays.asList(tourism)));
//
//        String[] healthcare = new String[] {"hospital"};
//        tagMap.put("Healthcare", new HashSet<String>(Arrays.asList(healthcare)));
//
//        String[] retail = new String[] {"stores", "retail", "shopping"};
//        tagMap.put("Retail", new HashSet<>(Arrays.asList(retail)));

        //put (keyword, tag)
        keywordMap.put("restaurants", "F&B");
        keywordMap.put("F&B", "F&B");
        keywordMap.put("eating", "F&B");
        keywordMap.put("dining", "F&B");
        keywordMap.put("drinks", "F&B");
        keywordMap.put("tourists", "Tourism");
        keywordMap.put("travel", "Tourism");
        keywordMap.put("corridor", "Tourism");
        keywordMap.put("vtl", "Tourism");
        keywordMap.put("hospital", "Healthcare");
        keywordMap.put("stores", "Retail");
        keywordMap.put("retail", "Retail");
        keywordMap.put("shopping", "Retail");
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
        System.out.println("Started driver to scrape for FAQ");

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

        driver.quit();
        return scrappedSrc;
    }

    /**
     * Find all the related tags for a given article.
     * @deprecated use scrapeMultipleArticlesForTags instead
     * @param articleURL
     */
    public List<String> scrapeArticleForTags(final String articleURL) {
        ChromeDriver driver = setDriver();
        System.out.println("Started driver to scrape for single news");

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

        driver.quit();
        return resultTag;
    }

    /**
     * Find all the related tags for a list of given articles, returns a map of article URLs and list of tags.
     * @param articleURLList
     */
    public Map<String, List<String>> scrapeMultipleArticlesForTags(final List<String> articleURLList) {
        ChromeDriver driver = setDriver();
        System.out.println("Started driver to scrape for multiple news");

        Map<String, List<String>> urlTagMap = new HashMap<>();

        for (String articleURL : articleURLList) {
            System.out.println("Scraping tags for " + articleURL);
            driver.get(articleURL);
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

            //Get the tags from the frequency map
            List<String> resultTag = getTags(frequencyMap);

            urlTagMap.put(articleURL, resultTag);

        }
        driver.quit();
        return urlTagMap;
    }

    private List<String> getTags(Map<String, Long> frequencyMap) {
        Set<String> tempTagSet = new HashSet<>();
        List<String> outputList = new ArrayList<>();
        //compare words with keyword map
        for (String keyword : keywordMap.keySet()) {
            if (frequencyMap.containsKey(keyword)) {
                //does not matter if tag is duplicated
                tempTagSet.add(keyword);
            }
        }

        System.out.println("temptagset: " + tempTagSet.toString());
        for (String keyword : tempTagSet) {
            if (!outputList.contains(keywordMap.get(keyword))) {
                outputList.add(keywordMap.get(keyword));
            }
        }
        return outputList;
    }

    ChromeDriver setDriver() {

        final ChromeOptions chromeOptions = new ChromeOptions();


        // For CI
//         System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");

        // For local IDE
//         System.setProperty("webdriver.chrome.driver", "backend/todo/src/main/resources/chromedriver.exe"); // Windows
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver"); // Mac

        // For deployment
//         System.setProperty("GOOGLE_CHROME_BIN", "/app/.apt/usr/bin/google-chrome");
//         System.setProperty("CHROMEDRIVER_PATH", "/app/.chromedriver/bin/chromedriver");

        // For both local and Deploy
        chromeOptions.addArguments("--headless");

        // For Deploy ONLY
        // chromeOptions.setBinary("/app/.apt/usr/bin/google-chrome");
        // chromeOptions.addArguments("--enable-javascript");
        // chromeOptions.addArguments("--disable-gpu");
        // chromeOptions.addArguments("--no-sandbox");
        return new ChromeDriver(chromeOptions);
    }
}
