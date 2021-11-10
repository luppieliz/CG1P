package com.app.todo.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.app.todo.faq.FAQController;
import com.app.todo.faq.FAQService;
import com.app.todo.measure.MeasureService;
import com.app.todo.scraper.ScraperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private FAQController faqController;
    private MeasureService measureService;

    @Autowired
    public ScheduledTasks(FAQController faqController, MeasureService measureService) {
        this.faqController = faqController;
        this.measureService = measureService;
    }

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "${scheduled.newsRefreshCron}")
    public void getNewsOnSchedule() {
        log.info("Fetching hourly news at {}", dateFormat.format(new Date()));
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