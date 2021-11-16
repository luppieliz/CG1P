package com.app.todo.notifier;

import com.app.todo.newsfunnel.News;
import com.app.todo.user.User;

import java.util.List;

public interface NotificationService {
    void sendEmailNotification(List<News> newsList, List<User> userList);

    void sendTextNotification(List<News> newsList, List<User> userList);

    void sendWrapper(List<News> newsList);

    String getTextMessage(List<News> newsList);
}
