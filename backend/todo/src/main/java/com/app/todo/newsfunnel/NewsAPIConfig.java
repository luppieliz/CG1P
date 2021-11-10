package com.app.todo.newsfunnel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
@Setter
@Getter
public class NewsAPIConfig {
    private final String URL = "https://newsapi.org/v2/top-headlines?";

    @Value("${newsAPI.API_KEY}")
    private String apiKey;

    private String country;
    private String query;

    public String getAPIQuery(String country, String query) {
        return URL + "country=" + country
                + "&" + "q=" + query
                + "&" + "apiKey=" + apiKey;
    }
    public String getAPIQueryv2(String sources, String query, String dateFrom) {
        return "https://newsapi.org/v2/everything?domains=" + sources
                + "&q=" + query
                + "&from=" + dateFrom
                + "&" + "apiKey=" + apiKey;
    }
    //https://newsapi.org/v2/everything?domains=channelnewsasia.com,straitstimes.com&from=2021-11-05&q=covid&apiKey=df40cdec224c4e9b8b6c98240f0cabb3
}
