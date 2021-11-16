package com.app.todo;

import com.app.todo.scraper.ScraperService;

import com.app.todo.scraper.ScraperServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ScraperServiceTest {

    @InjectMocks
    private ScraperServiceImpl scraperService;

    @Test
    void scrapeFAQ_Success() {
        String testURL = "https://www.enterprisesg.gov.sg/covid-19/safe-distance";
        List<String> test = scraperService.scrapeFAQ(testURL);
        assertNotNull(test);
    }

    @Test
    void scrapeArticle_Success() {
        String testURL = "https://www.channelnewsasia.com/singapore/covid-19-rules-fnb-closed-fined-singing-toasting-safe-distance-2261256";
        List<String> tagList = scraperService.scrapeArticleForTags(testURL);
        assertTrue(tagList.contains("F&B"));
    }
}
