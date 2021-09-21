package com.app.todo.sms;

import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsInitializer {

    private final SmsConfig smsConfig;

    private final static Logger LOGGER = LoggerFactory.getLogger(SmsInitializer.class);

    @Autowired
    public SmsInitializer(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
        Twilio.init(
                smsConfig.getAccountSID(),
                smsConfig.getAuthToken()
        );
        LOGGER.info("Twilio initialized with ... with sid {}", smsConfig.getAccountSID());
    }
}
