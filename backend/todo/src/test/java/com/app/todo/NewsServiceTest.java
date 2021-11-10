package com.app.todo;

import com.app.todo.newsfunnel.News;
import com.app.todo.newsfunnel.NewsAlreadyRetrievedException;
import com.app.todo.newsfunnel.NewsRepository;

import com.app.todo.newsfunnel.NewsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTest {
    @InjectMocks
    private NewsService newsService;

    @Mock
    private NewsRepository newsRepository;

    @Test
    void addNews_SameTitle_ReturnNewsAlreadyRetrievedException() {
        News news = new News("Covid is all time high!");
        newsRepository.save(news);
        when(newsRepository.existsByTitle(news.getTitle())).thenReturn(true);

        News testNews = new News("Covid is all time high!");

        Throwable exception = null;

        try {
            newsService.addNews(testNews);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(NewsAlreadyRetrievedException.class, exception.getClass());
        verify(newsRepository).existsByTitle(news.getTitle());
    }

    @Test
    void getAllNewsFromDate_ValidDate_ReturnNewsList() throws ParseException {
        News testNews1 = new News("Covid is all time high!", "2021-10-10T04:57:57Z");
        News testNews2 = new News("Covid is all time low!", "2021-10-11T04:57:57Z");
        List<News> testNewsList = new ArrayList<>();
        testNewsList.add(testNews1);
        testNewsList.add(testNews2);

        when(newsRepository.findAll()).thenReturn(testNewsList);

        String fromDate = "2021-10-10T04:57:57Z";
        List<News> newsList = newsService.getAllNewsFromDate(fromDate);

        assertEquals(testNewsList.size(), newsList.size());
    }

}
