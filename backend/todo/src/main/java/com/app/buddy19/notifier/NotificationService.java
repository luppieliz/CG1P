package com.app.buddy19.notifier;

import com.app.buddy19.newsfunnel.News;
import com.app.buddy19.user.User;

import java.util.List;

public interface NotificationService {
    void sendEmailNotification(List<News> newsList, List<User> userList);

    void sendTextNotification(List<News> newsList, List<User> userList);

    void sendWrapper(List<News> newsList);

    String getTextMessage(List<News> newsList);
}
