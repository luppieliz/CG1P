package com.app.todo.newsfunnel;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/newsapi")
public class NewsController {
    private NewsAPIService newsAPIService;
    private NewsService newsService;

    @Autowired
    public NewsController(NewsAPIService newsAPIService, NewsService newsService) {
        this.newsAPIService = newsAPIService;
        this.newsService = newsService;
    }

    @ApiOperation(value = "Get news with a specific country code and query")
    @GetMapping(path = "/{country_code}/{query}", produces = "application/json")
    public List<News> getNews(@PathVariable(value = "country_code") String countryCode,
                        @PathVariable(value="query") String query) throws IOException, InterruptedException, ParseException {
        NewsAPIResponse newsResponse = newsAPIService.getAPIResponse(countryCode,query);
        NewsDTO[] newsDTOArr = newsAPIService.getNewsDTOArr(newsResponse);
        List<News> resultNews = newsService.getNewsFromAPI(newsDTOArr);
        return resultNews;
    }

    @ApiOperation(value = "Get news from specific sources, from specific date, with query")
    @GetMapping(path = "/{sources}/{query}/{fromDate}", produces = "application/json")
    public List<News> getNewsv2(@PathVariable(value = "sources") String sources,
                              @PathVariable(value="query") String query, @PathVariable(value="fromDate") String fromDate) throws IOException, InterruptedException, ParseException {
        NewsAPIResponse newsResponse = newsAPIService.getAPIResponsev2(sources,query,fromDate);
        NewsDTO[] newsDTOArr = newsAPIService.getNewsDTOArr(newsResponse);
        List<News> resultNews = newsService.getNewsFromAPI(newsDTOArr);
        return resultNews;
    }

    @ApiOperation(value = "Get all news from database")
    @GetMapping(path = "/newsdb/all",  produces = "application/json")
    public List<News> getNewsFromDB() throws ParseException {
        return newsService.getAllNews();
    }

    @ApiOperation(value = "Get all news from database, from a given date")
    @GetMapping(path = "/newsdb/all/{dateFrom}",  produces = "application/json")
    public List<News> getNewsFromDBFromDate(@PathVariable(value = "dateFrom") final String dateFrom) throws ParseException {
        return newsService.getAllNewsFromDate(dateFrom);
    }


    @ApiOperation(value = "Demo email sender")
    @PostMapping(path = "/demoEmail", produces = "application/json")
    public void emoEmail() throws ParseException, IOException, InterruptedException {
        getNewsv2("channelnewsasia.com,straitstimes.com", "covid+singapore","2021-11-09");
    }


}
