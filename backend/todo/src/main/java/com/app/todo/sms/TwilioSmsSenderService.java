package com.app.todo.sms;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwilioSmsSenderService implements SmsSenderService{

    private final SmsConfig smsConfig;
    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSenderService.class);

    @Autowired
    public TwilioSmsSenderService(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        if (isPhoneNumberValid(smsRequest.getDestPhoneNumber())) {
            PhoneNumber to = new PhoneNumber(smsRequest.getDestPhoneNumber());
            PhoneNumber from = new PhoneNumber(smsConfig.getPhoneNumber());
            String msg = smsRequest.getMsg();

            MessageCreator msgCreator = new MessageCreator(to,from,msg);

            msgCreator.create();
            LOGGER.info("Sms sent: {}", smsRequest);
        } else {
            throw new IllegalArgumentException("Phone number " + smsRequest.getDestPhoneNumber() + " is not valid!" );
        }



    }

    private boolean isPhoneNumberValid(String destPhoneNumber) {
        return true;
    }
}
