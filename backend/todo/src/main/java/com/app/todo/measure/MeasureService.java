package com.app.todo.measure;

import java.util.List;

import com.app.todo.scraper.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasureService {

    private ScraperService scraperService;

    @Autowired
    public MeasureService(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    /**
     * Get measures sources from a website.
     * @param measureURL: url of the website containing all the required measures.
     * @return
     */
    public List<String> getMeasures(final String measureURL) {
        List<String> sourcesList = scraperService.scrapeFAQ(measureURL);
        return sourcesList;
    }

    /**
     * Get tag list for a news article.
     * @param newsURL: url of the news article.
     */
    public List<String> getTag(final String newsURL) {
        return scraperService.scrapeArticleForTags(newsURL);
    }
}