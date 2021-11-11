package com.app.todo.scheduler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.app.todo.faq.FAQController;
import com.app.todo.faq.FAQService;
import com.app.todo.measure.MeasureService;
import com.app.todo.newsfunnel.NewsController;
import com.app.todo.scraper.ScraperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private FAQController faqController;
    private MeasureService measureService;
    private NewsController newsController;

    @Autowired
    public ScheduledTasks(FAQController faqController, MeasureService measureService, NewsController newsController) {
        this.faqController = faqController;
        this.measureService = measureService;
        this.newsController = newsController;
    }

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");
    @Value("${scheduled.news.sources}")
    private String newsSources;
    @Value("${scheduled.news.query}")
    private String newsQuery;

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
            String testURL = "https://www.enterprisesg.gov.sg/covid-19/safe-distance";
            List<String> sourcesLink = measureService.getMeasures(testURL);
            sourcesLink.forEach(src -> System.out.println("Link scraped: " + src));

            faqController.updateFAQ(testURL);
        } catch(Exception e) {
            System.out.println("Error updating faq!");
            System.out.println(e);
        }
    }


}