package com.app.todo.newsfunnel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
@Setter
@Getter
public class NewsAPIConfig {
    private final String URL = "https://newsapi.org/v2/top-headlines?";
    private final String apiKey = "df40cdec224c4e9b8b6c98240f0cabb3";
    private String country;
    private String query;

    public String getAPIQuery(String country, String query) {
        return URL + "country=" + country
                + "&" + "q=" + query
                + "&" + "apiKey=" + apiKey;
    }
}
