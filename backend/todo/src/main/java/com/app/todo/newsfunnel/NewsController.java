package com.app.todo.newsfunnel;

import com.app.todo.measure.MeasureController;
import com.app.todo.measure.MeasureService;
import com.app.todo.phonetext.PhoneTextController;
import com.app.todo.phonetext.SmsRequest;
import com.app.todo.phonetext.TwilioSenderService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/newsapi")
public class NewsController {
    private NewsAPIService newsAPIService;
    private NewsService newsService;
    private TwilioSenderService senderService;
    private MeasureService measureService;

    @Autowired
    public NewsController(NewsAPIService newsAPIService, NewsService newsService,TwilioSenderService senderService, MeasureService measureService) {
        this.newsAPIService = newsAPIService;
        this.newsService = newsService;
        this.senderService = senderService;
        this.measureService = measureService;
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

    @ApiOperation(value = "Get all news from database")
    @GetMapping(path = "/newsdb/all",  produces = "application/json")
    public List<News> getNewsFromDB() {
        return newsService.getAllNews();
    }

    @ApiOperation(value = "Get all news from database, from a given date")
    @GetMapping(path = "/newsdb/all/{dateFrom}",  produces = "application/json")
    public List<News> getNewsFromDBFromDate(@PathVariable(value = "dateFrom") final String dateFrom) throws ParseException {
        return newsService.getAllNewsFromDate(dateFrom);
    }

    @ApiOperation(value = "Send SMS of new news retrieved")
    @PostMapping(path = "/{phone_number}", produces = "application/json")
    public void notifyNews(@PathVariable(value="phone_number") String phoneNo ) {
        List<News> currentNews = newsService.getAllNews();
        String message = newsService.getTextMessage(currentNews);
        senderService.sendSms(new SmsRequest(phoneNo,message));
    }

}
