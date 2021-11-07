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

    public List<String> getMeasures(String URL) {
        List<String> sourcesList = scraperService.scrapeFAQ(URL);
        return sourcesList;
    }

    public void getTag(String newsURL, List<String> tag) {
        scraperService.scrapeArticle(newsURL, tag);
    }
}
