package com.app.buddy19.faq;

import com.app.buddy19.scraper.ScraperService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FAQController {
    private ScraperService scraperService;
    private FAQService faqService;

    @Autowired
    public FAQController(ScraperService scraperService, FAQService faqService) {
        this.scraperService = scraperService;
        this.faqService = faqService;
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved a FAQ list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})


    @ApiOperation(value = "Retrieve a list of FAQs from a website", response = List.class)
    @GetMapping(path = "/faq/scrape", produces = "application/json")
    public List<FAQ> retrieveImage(@RequestParam String URL) {
        List<String> scrappedSrc = scraperService.scrapeFAQ(URL);
        return faqService.retrieveAllFAQ(scrappedSrc);
    }

    @ApiOperation(value = "Scrape all the updated FAQs from a website", response = List.class)
    @GetMapping(path = "/faq/update", produces = "application/json")
    public List<FAQ> updateFAQ(@RequestParam String URL) {
        List<String> scrappedSrc = scraperService.scrapeFAQ(URL);
        return faqService.updateFAQ(scrappedSrc);
    }

    @ApiOperation(value = "Retrieve all the FAQs stored in database", response = List.class)
    @GetMapping(path = "faq/retrieveFromDB", produces = "application/json")
    public List<FAQ> retrieveFromDB() {
        return faqService.getAllFAQ();
    }
}
