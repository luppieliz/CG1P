package com.app.todo.newsfunnel;

import com.app.todo.measure.MeasureService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    private NewsRepository newsRepository;
    private MeasureService measureService;
    final String STANDARD_DATE_FORMAT = "yyyy-MM-dd";

    @Autowired
    public NewsService(NewsRepository newsRepository, MeasureService measureService) {
        this.newsRepository = newsRepository;
        this.measureService = measureService;
    }

    /**
     * Add newly retrieved news article to database.
     * @param newNews
     * @return All newly retrieved news article.
     */
    public News addNews(News newNews) {
        if (newsRepository.existsByTitle(newNews.getTitle())) {
            throw new NewsAlreadyRetrievedException(newNews.getTitle());
        }
        return newsRepository.save(newNews);
    }

    /**
     * Retrieve all articles.
     * @return A list of all stored articles.
     */
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    /**
     * Parse all news articles from API call and store them into database.
     * @param newsFromAPI
     * @return: A list of newly retrieved articles.
     * @throws ParseException
     */
    public List<News> getNewsFromAPI(final NewsDTO[] newsFromAPI) throws ParseException {
        List<News> resultNews = new ArrayList<>();

        for (NewsDTO newsDTO : newsFromAPI) {
            News singleNews = new News();
            singleNews.setPublisher(newsDTO.getSource().get("name"));
            singleNews.setAuthor(newsDTO.getAuthor());
            singleNews.setTitle(newsDTO.getTitle());
            singleNews.setDescription(newsDTO.getDescription());
            singleNews.setURL(newsDTO.getUrl());
            singleNews.setPublishedDate(getFormattedDate(newsDTO.getPublishedAt()));
            singleNews.setContent(newsDTO.getContent());

            // Get tags for an article
            List<String> tagList = measureService.getTag(newsDTO.getUrl());

            // Convert tag list to string tag
            String stringTag = getStringTag(tagList);
            singleNews.setTagList(stringTag);

            resultNews.add(singleNews);
            addNews(singleNews);
        }
        return resultNews;
    }

    /**
     * Retrieve all news articles from a given date.
     * @param dateFrom
     * @return A list of news articles whose published dates start from the requested date.
     * @throws ParseException
     */
    public List<News> getAllNewsFromDate(final String dateFrom) throws ParseException {

        List<News> resultNewsList = new ArrayList<>();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(STANDARD_DATE_FORMAT);
        ZonedDateTime zdt = ZonedDateTime.parse(dateFrom);
        LocalDateTime ldtFrom = zdt.toLocalDateTime();
        String ldtString = ldtFrom.format(dateFormat);
        LocalDate ldFrom = LocalDate.parse(ldtString, dateFormat);

        List<News> newsList = newsRepository.findAll();

        for (News news : newsList) {
            zdt = ZonedDateTime.parse(news.getPublishedDate());
            LocalDateTime ldtNews = zdt.toLocalDateTime();
            ldtString = ldtNews.format(dateFormat);
            LocalDate comparedDate = LocalDate.parse(ldtString, dateFormat);

            if (comparedDate.compareTo(ldFrom) >= 0) {
                resultNewsList.add(news);
            }
        }
        return resultNewsList;
    }

    /**
     * Combine a list of tags into a string.
     * @param tagList
     * @return A string of tags.
     */
    public String getStringTag(final List<String> tagList) {
        String tempStringTag = "";

        for (int i = 0; i < tagList.size() - 1; i++) {
            tempStringTag += tagList.get(i) + ",";
        }
        if (!tagList.isEmpty()) {
            tempStringTag += tagList.get(tagList.size() - 1);
        }

        return tempStringTag;
    }

    /**
     * Craft a text message given a list of news articles.
     * @param newsList
     * @return A phone message created that indexed all the news articles.
     */
    public String getTextMessage(final List<News> newsList) {
        String message = "Check out the latest COVID news:\n";
        int idx = 1;

        for (News news: newsList) {
            message += idx + ". " + news.getURL() + "\n";
            idx++;
        }
        return message;
    }

    /**
     * Format a Date.
     * @param date
     * @return A string date in a standard format.
     * @throws ParseException
     */
    public String getFormattedDate(String date) throws ParseException {
        ZonedDateTime zdt = ZonedDateTime.parse(date);
        LocalDateTime dateTime = zdt.toLocalDateTime();
        DateTimeFormatter newDateFormat = DateTimeFormatter.ofPattern(STANDARD_DATE_FORMAT);
        return dateTime.format(newDateFormat);
    }
}
