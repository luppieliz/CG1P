package com.app.todo.newsfunnel;

import com.app.todo.measure.MeasureController;
import com.app.todo.phonetext.PhoneTextController;
import com.app.todo.phonetext.SmsRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.swagger.annotations.ApiOperation;
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
    private MeasureController measureController;

    @Autowired
    public NewsController(NewsAPIService apiService, NewsService newsService,PhoneTextController textController, MeasureController measureController) {
        this.apiService = apiService;
        this.newsService = newsService;
        this.textController = textController;
        this.measureController = measureController;
    }

    @ApiOperation(value = "Get news with a specific country code and query")
    @GetMapping(path = "/{country_code}/{query}", produces = "application/json")
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

            List<String> tagList = new ArrayList<>();
            measureController.getTag(newsDTO.getUrl(), tagList);

            String arrTag = "";

            for (int i = 0; i < tagList.size() - 1; i++) {
                System.out.println(tagList.get(i));
                arrTag += tagList.get(i) + ",";
            }
            if (!tagList.isEmpty()) {
                arrTag += tagList.get(tagList.size() - 1);
            }

            singleNews.setTagList(arrTag);

            resultNews.add(singleNews);
            newsService.addNews(singleNews);
        }

        return resultNews;
    }

    @ApiOperation(value = "Get news with specific tags from database")
    @GetMapping(path = "/newsdb/{tags}",  produces = "application/json") //check exceptions
    public List<News> getNewsFromDB(@PathVariable(value = "tags") String tags) {
        //process tags
        //preferred format {tags} = (String): "tag1+tag2+tag3" or all
        if (tags.equals("all")) {
            return newsService.getAllNews();
        } else {
            //process tags
            String [] tagList = tags.split("\\+");
            return new ArrayList<>(); //temporary
//            return newsService.getNewsWithTags(tagList); TODO
        }

    }

    @ApiOperation(value = "Send SMS of new news retrieved")
    @PostMapping(path = "/{phone_number}", produces = "application/json")
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
