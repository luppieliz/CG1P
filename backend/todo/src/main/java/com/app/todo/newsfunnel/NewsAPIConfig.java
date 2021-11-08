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
}
