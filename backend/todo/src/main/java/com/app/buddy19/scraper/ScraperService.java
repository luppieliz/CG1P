package com.app.buddy19.scraper;

import java.util.List;
import java.util.Map;

public interface ScraperService {
    List<String> scrapeFAQ(String faqURL);

    List<String> scrapeArticleForTags(String articleURL);

    Map<String, List<String>> scrapeMultipleArticlesForTags(List<String> articleURLList);
}
