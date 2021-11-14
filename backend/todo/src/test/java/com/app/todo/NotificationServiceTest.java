package com.app.todo;

import com.app.todo.business.Business;
import com.app.todo.business.BusinessService;
import com.app.todo.email.EmailService;
import com.app.todo.industry.Industry;
import com.app.todo.newsfunnel.News;
import com.app.todo.notifier.NotificationService;
import com.app.todo.phonetext.SmsRequest;
import com.app.todo.phonetext.TwilioSenderService;
import com.app.todo.user.User;
import com.app.todo.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private EmailService emailService;

    @Mock
    private UserService userService;

    @Mock
    private BusinessService businessService;

    @Mock
    private TwilioSenderService senderService;

    @Test
    void sendEmailNotfication_Success() {
        News new1 = new News("Covid is all time high!");
        News new2 = new News("Covid is all time low!");
        List<News> newsList = new ArrayList<>();
        newsList.add(new1);
        newsList.add(new2);

        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);

        User user1 = new User("alice@gmail.com", "alice", "goodpassword", false,false, "ROLE_BUSINESSOWNER", business);
        User user2 = new User("bob@gmail.com", "bob", "goodpassword", false,false, "ROLE_EMPLOYEE", business);
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        String emailBody = "Here are some articles for you to check out!\n";
        String subject = "News updates from Buddy-19!";

        for(News news : newsList) {
            emailBody += news.getURL() + "\n";
        }


        notificationService.sendEmailNotification(newsList, userList);

        verify(emailService).sendSimpleEmail(userList.get(0).getEmail(), subject, emailBody);
        verify(emailService).sendSimpleEmail(userList.get(1).getEmail(), subject, emailBody);
    }

    @Test
    void sendTextNotification_Success() {
        News new1 = new News("Covid is all time high!");
        News new2 = new News("Covid is all time low!");
        List<News> newsList = new ArrayList<>();
        newsList.add(new1);
        newsList.add(new2);

        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);

        User user1 = new User("+65 9999 8888","alice@gmail.com", "alice", "goodpassword", false,false, "ROLE_BUSINESSOWNER");
        User user2 = new User("+65 8888 9999","bob@gmail.com", "bob", "goodpassword", false,false, "ROLE_EMPLOYEE");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        String message = "Check out the latest COVID news:\n";
        int idx = 1;

        for (News news: newsList) {
            message += idx + ". " + news.getURL() + "\n";
            idx++;
        }

        notificationService.sendTextNotification(newsList, userList);

        verify(senderService).sendSms( new SmsRequest(userList.get(0).getPhone(),message));
        verify(senderService).sendSms( new SmsRequest(userList.get(1).getPhone(),message));
    }
}
