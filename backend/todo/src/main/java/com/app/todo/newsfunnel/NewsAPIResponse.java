package com.app.todo.newsfunnel;

import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
public class NewsAPIResponse {
    private String status;
    private Integer totalResults;
    private JsonElement articles;

    public NewsAPIResponse() {}

    @Override
    public String toString() {
        return "NewsAPIResponse{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", articles=" + articles +
                '}';
    }
}
