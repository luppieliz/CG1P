package com.app.buddy19.newsfunnel;

import com.app.buddy19.notifier.NotificationService;
import com.app.buddy19.scraper.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class NewsServiceImpl implements NewsService {

    final String STANDARD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private NewsRepository newsRepository;
    private ScraperService scraperService;
    private NotificationService notificationService;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, ScraperService scraperService, NotificationService notificationService) {
        this.newsRepository = newsRepository;
        this.scraperService = scraperService;
        this.notificationService = notificationService;
    }

    /**
     * Add newly retrieved news article to database.
     *
     * @param newNews
     * @return All newly retrieved news article.
     */
    @Override
    public News addNews(News newNews) {
        if (newsRepository.existsByTitle(newNews.getTitle())) {
            throw new NewsAlreadyRetrievedException(newNews.getTitle());
        }
        return newsRepository.save(newNews);
    }

    /**
     * Retrieve all articles, sorts by date
     *
     * @return A list of all stored articles.
     */
    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    /**
     * Parse all news articles from API call and store them into database. Skips over duplicates
     *
     * @param newsFromAPI
     * @throws ParseException
     * @return: A list of newly retrieved articles.
     */
    @Override
    public List<News> getNewsFromAPI(final NewsDTO[] newsFromAPI) throws ParseException {
        //get a list of URLS that are not duplicated
        List<String> urlList = new ArrayList<>();
        for (NewsDTO newsDTO : newsFromAPI) {
            //check if news item is already in the repo
            if (!newsRepository.existsByURL(newsDTO.getUrl())) {
                //filter out non-singapore articles
                if (newsDTO.getUrl()
                           .contains("/singapore")) {
                    urlList.add(newsDTO.getUrl());
                }
            }
        }

        //get a map of all tags for each URL
        Map<String, List<String>> tagMap = scraperService.scrapeMultipleArticlesForTags(urlList);

        List<News> resultNews = new ArrayList<>();
        for (NewsDTO newsDTO : newsFromAPI) {
            //check if news item is already in the repo
            if (newsRepository.existsByURL(newsDTO.getUrl())) {
                System.out.println("Article already in repo! URL: " + newsDTO.getUrl());
                continue;
            }


            News singleNews = new News();
            singleNews.setPublisher(newsDTO.getSource()
                                           .get("name"));
            singleNews.setAuthor(newsDTO.getAuthor());
            singleNews.setTitle(newsDTO.getTitle());
            singleNews.setDescription(newsDTO.getDescription());
            singleNews.setURL(newsDTO.getUrl());
            singleNews.setPublishedDate(getFormattedDate(newsDTO.getPublishedAt()));
            singleNews.setContent(newsDTO.getContent());
            singleNews.setUrlToImage(newsDTO.getUrlToImage());

            // Get tags for an article
            List<String> tagList = tagMap.get(newsDTO.getUrl());
            System.out.println("successfully got tags for " + newsDTO.getUrl());

            // Convert tag list to string tag
            String stringTag = getStringTag(tagList);
            singleNews.setTagList(stringTag);

            resultNews.add(singleNews);
            try {
                addNews(singleNews);
            } catch (NewsAlreadyRetrievedException e) {
                System.out.println(e.getMessage());
            }
        }

        notificationService.sendWrapper(resultNews);
        return resultNews;
    }

    /**
     * Retrieve all news articles from a given date.
     *
     * @param dateFrom
     * @return A list of news articles whose published dates start from the requested date.
     * @throws ParseException
     */
    @Override
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
     *
     * @param tagList
     * @return A string of tags.
     */
    @Override
    public String getStringTag(final List<String> tagList) {
        if (tagList == null) {
            return "";
        }
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
     * Format a Date.
     *
     * @param date
     * @return A string date in a standard format.
     * @throws ParseException
     */
    @Override
    public String getFormattedDate(String date) throws ParseException {
        ZonedDateTime zdt = ZonedDateTime.parse(date);
        LocalDateTime dateTime = zdt.toLocalDateTime();
        DateTimeFormatter newDateFormat = DateTimeFormatter.ofPattern(STANDARD_DATE_FORMAT);
        return dateTime.format(newDateFormat);
    }
}
