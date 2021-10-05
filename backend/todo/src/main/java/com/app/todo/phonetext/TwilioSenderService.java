package com.app.todo.phonetext;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Arrays;

@Service
public class TwilioSenderService implements SenderService {

    private final TwilioConfig twilioConfig;
    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioSenderService.class);

    @Autowired
    public TwilioSenderService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        if (isPhoneNumberValid(smsRequest.getDestPhoneNumber())) {
            PhoneNumber to = new PhoneNumber(smsRequest.getDestPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
            String msg = smsRequest.getMessage();

            MessageCreator msgCreator = new MessageCreator(to,from,msg);

            msgCreator.create();
            LOGGER.info("Sms sent: {}", smsRequest);
        } else {
            throw new IllegalArgumentException("Phone number " + smsRequest.getDestPhoneNumber() + " is not valid!" );
        }
    }

    public void sendMms(MmsRequest MmsRequest) {
        if (isPhoneNumberValid(MmsRequest.getDestPhoneNumber())) {
            Message message = Message.creator(
                            new PhoneNumber(MmsRequest.getDestPhoneNumber()),
                            new PhoneNumber(twilioConfig.getPhoneNumber()),
                            MmsRequest.getMessage())
                    .setMediaUrl(
                            Arrays.asList(URI.create(MmsRequest.getImageURL())))
                    .create();
            LOGGER.info("Sms sent: {}", MmsRequest);
        } else {
            throw new IllegalArgumentException("Phone number " + MmsRequest.getDestPhoneNumber() + " is not valid!" );
        }
    }

    private boolean isPhoneNumberValid(String destPhoneNumber) {
        return true;
    }
}
