package com.app.buddy19.newsfunnel;

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
    @Value("${api.url.newsapi}")
    private String URL;

    @Value("${newsAPI.API_KEY}")
    private String apiKey;

    private String country;
    private String query;

    public String getAPIQuery(String country, String query) {
        return URL + "/top-headlines?country=" + country
                + "&" + "q=" + query
                + "&" + "apiKey=" + apiKey;
    }

    public String getAPIQueryv2(String sources, String query, String dateFrom) {
        return URL + "/everything?domains=" + sources
                + "&q=" + query
                + "&from=" + dateFrom
                + "&" + "apiKey=" + apiKey;
    }
}
