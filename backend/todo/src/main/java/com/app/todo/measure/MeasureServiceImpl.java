package com.app.todo.measure;

import java.util.List;
import java.util.Map;

import com.app.todo.scraper.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasureServiceImpl implements MeasureService {

    private ScraperService scraperService;

    @Autowired
    public MeasureServiceImpl(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    /**
     * Get measures sources from a website.
     * @param measureURL: url of the website containing all the required measures.
     * @return list of measure urls
     */
    @Override
    public List<String> getMeasures(final String measureURL) {
        return scraperService.scrapeFAQ(measureURL);
    }

    /**
     * Get tag list for a single news article.
     * @param newsURL: url of the news article.
     */
    @Override
    public List<String> getTag(final String newsURL) {
        return scraperService.scrapeArticleForTags(newsURL);
    }

    /**
     * Get tag list for a multiple news articles.
     * @param newsURLList: list of urls of news articles.
     */
    @Override
    public Map<String,List<String>> getTagMap(final List<String> newsURLList) {
        return scraperService.scrapeMultipleArticlesForTags(newsURLList);
    }
}