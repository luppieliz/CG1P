package com.app.todo.faq;

import com.app.todo.scrapper.ScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FAQController {
    private ScrapperService scrapperService;
    private FAQService faqService;


    @Autowired
    public FAQController(ScrapperService scrapperService, FAQService faqService) {
        this.scrapperService = scrapperService;
        this.faqService = faqService;
    }

    @GetMapping(path = "faq/scrape/{URL}")
    public void retrieveImage(@PathVariable(value = "URL") String URL) {
        List<String> scrappedSrc = scrapperService.scrapeFAQ(URL);
        for (String url : scrappedSrc) {
            faqService.addFAQ(url);
        }
    }
}
