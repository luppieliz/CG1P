package com.app.todo.newsfunnel;

import com.google.gson.JsonElement;

import java.util.Arrays;

public class NewsAPIResponse {
    private String status;
    private Integer totalResults;
    private JsonElement articles;

    public NewsAPIResponse() {}

    public NewsAPIResponse(String status, Integer totalResults, JsonElement articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "NewsAPIResponse{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", articles=" + articles +
                '}';
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public JsonElement getArticles() {
        return articles;
    }

    public void setArticles(JsonElement articles) {
        this.articles = articles;
    }

}
