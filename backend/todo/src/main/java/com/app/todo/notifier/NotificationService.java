package com.app.todo.notifier;

import com.app.todo.business.Business;
import com.app.todo.business.BusinessService;
import com.app.todo.email.EmailService;
import com.app.todo.industry.Industry;
import com.app.todo.newsfunnel.News;
import com.app.todo.user.User;
import com.app.todo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Autowired
    public NotificationService(EmailService emailService, UserService userService, BusinessService businessService) {
        this.emailService = emailService;
        this.userService = userService;
        this.businessService = businessService;
    }

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

    public void sendTextNotification(List<News> newsList, List<User> userList) {
        //todo
    }

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
}
