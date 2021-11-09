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
    final String SG_PHONE_CODE = "+65";
    final int NO_DIGITS = 8;


    @Autowired
    public TwilioSenderService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    /**
     * Send an SMS with a given SMS request. Throw IllegalArgumentException if phone number is invalid.
     * @param smsRequest
     */
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

    /**
     * Send an MMS with a given MMS request. Throw IllegalArgumentException if phone number is invalid.
     * @param MmsRequest
     */
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

    /**
     * Validate whether a phone number is a valid SG phone number.
     * @param destPhoneNumber
     * @return True if a phone number contains "+65" and has a total of 8 digits.
     */
    private boolean isPhoneNumberValid(final String destPhoneNumber) {

        if (!destPhoneNumber.contains(SG_PHONE_CODE)) {
            return false;
        }

        // Start after +65
        int strIdx = destPhoneNumber.indexOf(SG_PHONE_CODE) + 3;
        int totalDigits = 0;

        for (int i = strIdx; i < destPhoneNumber.length(); i++) {
            if(Character.isSpaceChar(destPhoneNumber.charAt(i))) {
                continue;
            }

            if (Character.isDigit(destPhoneNumber.charAt(i))) {
                totalDigits += 1;
            } else {
                return false;
            }
        }

        if (totalDigits != NO_DIGITS) {
            return false;
        }

        return true;
    }
}
