package com.app.todo.phonetext;

public interface SenderService {
    void sendSms(SmsRequest smsRequest);
    void sendMms(MmsRequest MmsRequest);
}
