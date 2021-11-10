package com.app.todo;

import com.app.todo.scraper.ScraperService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ScraperServiceTest {

    @InjectMocks
    private ScraperService scrapperService;

    @Test
    void scrapeFAQ_Success() {
        String testURL = "https://www.enterprisesg.gov.sg/covid-19/safe-distance";
        List<String> srcScrapped = new ArrayList<>();
        srcScrapped.add("https://www.enterprisesg.gov.sg/-/media/esg/images/covid-19/safe-distance/for-businesses-safe-distancing_fb_english.png?la=en&hash=6698C11548714E743A22D8F23612ED6B6912FAE2");
        List<String> test = scrapperService.scrapeFAQ(testURL);
        assertTrue(test.contains(srcScrapped.get(0)));
    }

    @Test
    void scrapeArticle_Success() {
        String testURL = "https://www.channelnewsasia.com/singapore/covid-19-rules-fnb-closed-fined-singing-toasting-safe-distance-2261256";
        List<String> tagList = scrapperService.scrapeArticleForTags(testURL);
        assertTrue(tagList.contains("F&B"));
    }
}
