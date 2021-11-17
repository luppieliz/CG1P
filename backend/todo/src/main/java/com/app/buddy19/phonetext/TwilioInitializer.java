package com.app.buddy19.phonetext;

import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitializer {

    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);

    @Autowired
    public TwilioInitializer(TwilioConfig twilioConfig) {
        Twilio.init(
                twilioConfig.getAccountSID(),
                twilioConfig.getAuthToken()
        );
        LOGGER.info("Twilio initialized with sid {}", twilioConfig.getAccountSID());
    }
}