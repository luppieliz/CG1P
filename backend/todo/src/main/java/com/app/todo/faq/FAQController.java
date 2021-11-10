package com.app.todo.faq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.app.todo.scraper.ScraperService;

@RestController
public class FAQController {
    private ScraperService scraperService;
    private FAQService faqService;

    @Autowired
    public FAQController(ScraperService scraperService, FAQService faqService) {
        this.scraperService = scraperService;
        this.faqService = faqService;
    }

    @GetMapping(path = "/faq/scrape")
    public List<FAQ> retrieveImage(@RequestParam String URL) {
        List<String> scrappedSrc = scraperService.scrapeFAQ(URL);
        return faqService.retrieveAllFAQ(scrappedSrc);
    }

    @GetMapping(path = "/faq/update")
    public List<FAQ> updateFAQ(@RequestParam String URL) {
        List<String> scrappedSrc = scraperService.scrapeFAQ(URL);
        return faqService.updateFAQ(scrappedSrc);
    }


    @GetMapping(path = "faq/retrieveFromDB")
    public List<FAQ> retrieveFromDB() {
        return faqService.getAllFAQ();
    }
}
