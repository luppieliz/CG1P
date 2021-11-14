package com.app.todo.notifier;

import com.app.todo.business.Business;
import com.app.todo.business.BusinessService;
import com.app.todo.email.EmailService;
import com.app.todo.newsfunnel.News;
import com.app.todo.phonetext.SmsRequest;
import com.app.todo.phonetext.TwilioSenderService;
import com.app.todo.user.User;
import com.app.todo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {
    private EmailService emailService;
    private UserService userService;
    private BusinessService businessService;
    private TwilioSenderService senderService;

    @Autowired
    public NotificationService(EmailService emailService, UserService userService, BusinessService businessService, TwilioSenderService senderService) {
        this.emailService = emailService;
        this.userService = userService;
        this.businessService = businessService;
        this.senderService = senderService;
    }

    /**
     * Send newly updated news to all user via email
     * @param newsList
     * @param userList
     */
    public void sendEmailNotification(List<News> newsList, List<User> userList) {
        String emailBody = "Here are some articles for you to check out!\n";

        //generate email body
        for(News news : newsList) {
            emailBody += news.getURL() + "\n";
        }

        //send email
        for (User user : userList) {
            emailService.sendSimpleEmail(user.getEmail(), "News updates from Buddy-19!", emailBody);
        }

    }

    /**
     * Send newly updated news to all users via text
     * @param newsList
     * @param userList
     */
    public void sendTextNotification(List<News> newsList, List<User> userList) {
        String message = getTextMessage(newsList);

        for (User user : userList) {
            senderService.sendSms(new SmsRequest(user.getPhone(), message));
        }
    }

    /**
     * Send email and text of articles according to user's registered industry
     * @param newsList
     */
    public void sendWrapper(List<News> newsList) {
        Map<String, List<News>> industryNewsMap = new HashMap<>();

        //groups news by industry
        for (News news : newsList) {
            String[] tags = news.getTagList().split(",");
            for (String tag : tags) {
                industryNewsMap.computeIfAbsent(tag, k -> new ArrayList<>()).add(news);
            }
        }

        //get all users through businesses by industry
        for (String industry : industryNewsMap.keySet()) {
            List<Business> businessList = businessService.getAllBusinessByIndustryName(industry);
            List<User> userList = new ArrayList<>();
            for (Business business : businessList) {
                userList.addAll(userService.getUsersByBusiness(business.getId()));
            }
            sendEmailNotification(industryNewsMap.get(industry), userList);
            sendTextNotification(industryNewsMap.get(industry), userList);
        }
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
}
