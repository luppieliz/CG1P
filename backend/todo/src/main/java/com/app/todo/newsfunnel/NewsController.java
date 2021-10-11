package com.app.todo.newsfunnel;

import com.app.todo.phonetext.PhoneTextController;
import com.app.todo.phonetext.SmsRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/newsapi")
public class NewsController {
    private NewsAPIService apiService;
    private NewsService newsService;
    private PhoneTextController textController;

    @Autowired
    public NewsController(NewsAPIService apiService, NewsService newsService,PhoneTextController textController) {
        this.apiService = apiService;
        this.newsService = newsService;
        this.textController = textController;
    }

    @GetMapping("/{country_code}/{query}")
    public List<News> getNews(@PathVariable(value = "country_code") String countryCode,
                        @PathVariable(value="query") String query) throws IOException, InterruptedException {
        NewsAPIResponse newsResponse = apiService.getAPIResponse(countryCode,query);

        Gson gson = new Gson();
        NewsDTO[] news = new Gson().fromJson(newsResponse.getArticles(), NewsDTO[].class);

        List<News> resultNews = new ArrayList<>();

        for (NewsDTO newsDTO : news) {
            News singleNews = new News();
            singleNews.setPublisher(newsDTO.getSource().get("name"));
            singleNews.setAuthor(newsDTO.getAuthor());
            singleNews.setTitle(newsDTO.getTitle());
            singleNews.setDescription(newsDTO.getDescription());
            singleNews.setURL(newsDTO.getUrl());
            singleNews.setPublishedDate(newsDTO.getPublishedAt());
            singleNews.setContent(newsDTO.getContent());
            resultNews.add(singleNews);
            newsService.addNews(singleNews);
        }

        return resultNews;
    }

    @PostMapping("/{phone_number}")
    public void notifyNews(@PathVariable(value="phone_number") String phoneNo ) {
        List<News> currentNews = newsService.getAllNews();
        String message = "Check out the latest COVID news:\n";
        int index = 1;

        for (News news: currentNews) {
            message += index + ". " + news.getURL() + "\n";
            index++;
        }

        SmsRequest sms = new SmsRequest(phoneNo,message);
        textController.sendSms(sms);
    }


}
