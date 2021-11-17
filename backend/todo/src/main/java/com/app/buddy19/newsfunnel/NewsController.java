package com.app.buddy19.newsfunnel;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved news article list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})

    @ApiOperation(value = "Get news with a specific country code and query", response = List.class)
    @GetMapping(path = "/{country_code}/{query}", produces = "application/json")
    public List<News> getNews(@PathVariable(value = "country_code") String countryCode,
                              @PathVariable(value = "query") String query) throws IOException, InterruptedException, ParseException {
        NewsAPIResponse newsResponse = newsAPIService.getAPIResponse(countryCode, query);
        NewsDTO[] newsDTOArr = newsAPIService.getNewsDTOArr(newsResponse);
        List<News> resultNews = newsService.getNewsFromAPI(newsDTOArr);
        return resultNews;
    }

    @ApiOperation(value = "Get news from specific sources, from specific date, with query", response = List.class)
    @GetMapping(path = "/{sources}/{query}/{fromDate}", produces = "application/json")
    public List<News> getNewsv2(@PathVariable(value = "sources") String sources,
                                @PathVariable(value = "query") String query, @PathVariable(value = "fromDate") String fromDate) throws IOException, InterruptedException, ParseException {
        NewsAPIResponse newsResponse = newsAPIService.getAPIResponsev2(sources, query, fromDate);
        NewsDTO[] newsDTOArr = newsAPIService.getNewsDTOArr(newsResponse);
        List<News> resultNews = newsService.getNewsFromAPI(newsDTOArr);
        return resultNews;
    }

    @ApiOperation(value = "Get all news from database", response = List.class)
    @GetMapping(path = "/newsdb/all", produces = "application/json")
    public List<News> getNewsFromDB() throws ParseException {
        return newsService.getAllNews();
    }

    @ApiOperation(value = "Get all news from database, from a given date", response = List.class)
    @GetMapping(path = "/newsdb/all/{dateFrom}", produces = "application/json")
    public List<News> getNewsFromDBFromDate(@PathVariable(value = "dateFrom") final String dateFrom) throws ParseException {
        return newsService.getAllNewsFromDate(dateFrom);
    }


    @ApiOperation(value = "Demo email sender")
    @PostMapping(path = "/demoEmail", produces = "application/json")
    public void emoEmail() throws ParseException, IOException, InterruptedException {
        getNewsv2("channelnewsasia.com,straitstimes.com", "covid+singapore", "2021-11-09");
    }


}
