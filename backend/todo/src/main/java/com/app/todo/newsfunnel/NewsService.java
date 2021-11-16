package com.app.todo.newsfunnel;

import java.text.ParseException;
import java.util.List;

public interface NewsService {
    News addNews(News newNews);

    List<News> getAllNews() throws ParseException;

    List<News> getNewsFromAPI(NewsDTO[] newsFromAPI) throws ParseException;

    List<News> getAllNewsFromDate(String dateFrom) throws ParseException;

    String getStringTag(List<String> tagList);

    String getFormattedDate(String date) throws ParseException;
}
