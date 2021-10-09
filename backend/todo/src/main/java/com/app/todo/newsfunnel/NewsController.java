package com.app.todo.newsfunnel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class NewsController {
    private NewsAPIService apiService;
    private NewsService newsService;

    @Autowired
    public NewsController(NewsAPIService apiService, NewsService newsService) {
        this.apiService = apiService;
        this.newsService = newsService;
    }

    // country = sg, query = anythingggg
    @GetMapping("/news/{country_code}/{query}")
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
//            System.out.println(newsService.addNews(singleNews));
        }

        return resultNews;
    }
}
