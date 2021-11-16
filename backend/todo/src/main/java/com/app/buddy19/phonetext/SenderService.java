package com.app.buddy19.phonetext;

public interface SenderService {
    void sendSms(SmsRequest smsRequest);

    void sendMms(MmsRequest MmsRequest);
}
