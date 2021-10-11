package com.app.todo.newsfunnel;

import com.app.todo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    private NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News addNews(News newNews) {
        return newsRepository.save(newNews);
    }
}
