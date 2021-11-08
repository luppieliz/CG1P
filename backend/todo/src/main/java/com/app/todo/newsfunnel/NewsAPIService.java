package com.app.todo.newsfunnel;

import com.app.todo.covid.DailyResponse;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class NewsAPIService {
    private final NewsAPIConfig newsAPIConfig;

    @Autowired
    public NewsAPIService(NewsAPIConfig newsAPIConfig) {
        this.newsAPIConfig = newsAPIConfig;
    }

    public NewsAPIResponse getAPIResponse(String countryCode, String query) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(newsAPIConfig.getAPIQuery(countryCode, query)))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        NewsAPIResponse newsResponse = new Gson().fromJson(response.body(), NewsAPIResponse.class);
        return newsResponse;
    }
}
