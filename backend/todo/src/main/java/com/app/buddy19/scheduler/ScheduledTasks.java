package com.app.buddy19.scheduler;

import com.app.buddy19.faq.FAQController;
import com.app.buddy19.newsfunnel.NewsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
    private FAQController faqController;
    private NewsController newsController;
    @Value("${scheduled.news.sources}")
    private String newsSources;
    @Value("${scheduled.news.query}")
    private String newsQuery;
    @Value("${api.url.faq}")
    private String faqUrl;
    @Autowired
    public ScheduledTasks(FAQController faqController, NewsController newsController) {
        this.faqController = faqController;
        this.newsController = newsController;
    }

    @Scheduled(cron = "${scheduled.newsRefreshCron}")
    public void getNewsOnSchedule() throws IOException, ParseException, InterruptedException {
        log.info("Fetching hourly news at {}", dateFormat.format(new Date()));
        String fromDate = dateFormatDate.format(new Date(System.currentTimeMillis() - 86400 * 1000));
        newsController.getNewsv2(newsSources, newsQuery, fromDate);
    }

    @Scheduled(cron = "${scheduled.FAQRefreshCron}")
    public void getFAQUpdatesOnSchedule() {
        log.info("Fetching FAQ updates at {}", dateFormat.format(new Date()));
        try {
            faqController.updateFAQ(faqUrl);
        } catch (Exception e) {
            System.out.println("Error updating faq!");
            System.out.println(e);
        }
    }


}