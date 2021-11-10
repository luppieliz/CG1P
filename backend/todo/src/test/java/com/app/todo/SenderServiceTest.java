package com.app.todo;

import com.app.todo.phonetext.*;
import com.twilio.exception.ApiException;
import com.twilio.exception.AuthenticationException;
import com.twilio.exception.TwilioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class SenderServiceTest {
    @InjectMocks
    private TwilioSenderService senderService;

    @Mock
    private TwilioConfig config;

    @Mock
    private TwilioInitializer init;
    /* Test for SMS */

    @Test
    void sendSMS_InvalidNumberWithCountryCodeWithNoSpaceValidMessage_ReturnIllegalArgumentException() {
        SmsRequest testSMSRequest = new SmsRequest("+65999988888", "This is a test message");

        Throwable testException = null;

        try {
            senderService.sendSms(testSMSRequest);
        } catch (Throwable exception) {
            testException = exception;
        }

        assertEquals(IllegalArgumentException.class, testException.getClass());
    }

    @Test
    void sendSMS_InvalidNumberWithCountryCodeWithSpaceValidMessage_ReturnIllegalArgumentException() {
        SmsRequest testSMSRequest = new SmsRequest("+65 9999 88888", "This is a test message");

        Throwable testException = null;

        try {
            senderService.sendSms(testSMSRequest);
        } catch (Throwable exception) {
            testException = exception;
        }

        assertEquals(IllegalArgumentException.class, testException.getClass());
    }

    @Test
    void sendSMS_InvalidNumberWithoutCountryCodeWithoutSpaceValidMessage_ReturnIllegalArgumentException() {
        SmsRequest testSMSRequest = new SmsRequest("999988888", "This is a test message");

        Throwable testException = null;

        try {
            senderService.sendSms(testSMSRequest);
        } catch (Throwable exception) {
            testException = exception;
        }

        assertEquals(IllegalArgumentException.class, testException.getClass());
    }

    @Test
    void sendSMS_InvalidNumberWithoutCountryCodeWithSpaceValidMessage_ReturnIllegalArgumentException() {
        SmsRequest testSMSRequest = new SmsRequest("9999 88888", "This is a test message");

        Throwable testException = null;

        try {
            senderService.sendSms(testSMSRequest);
        } catch (Throwable exception) {
            testException = exception;
        }

        assertEquals(IllegalArgumentException.class, testException.getClass());
    }

    @Test
    void sendSMS_ValidNumberAboveMaxMessageLength_ReturnAuthenticationException() {
        SmsRequest testSMSRequest = new SmsRequest("+65 9999 8888", "This is a test message that is so long and it exceeds the number of length and it is not a valid message! You should not be using this kind of message!");

        Throwable testException = null;

        try {
            senderService.sendSms(testSMSRequest);
        } catch (Throwable exception) {
            testException = exception;
        }

        assertNotNull(testException);
    }

    @Test
    void sendSMS_ValidNumberBelowMinMessageLength_ReturnAuthenticationException() {
        SmsRequest testSMSRequest = new SmsRequest("+65 9999 8888", "     ");

        Throwable testException = null;

        try {
            senderService.sendSms(testSMSRequest);
        } catch (Throwable exception) {
            testException = exception;
        }

        assertNotNull(testException);
    }


    /* Test for MMS */
    @Test
    void sendMMS_InvalidNumberWithCountryCodeWithNoSpaceValidMessageNonEmptyURL_ReturnIllegalArgumentException() {
        MmsRequest testMMSRequest = new MmsRequest("+65999988888", "This is a test message","https://picsum.photos/200");

        Throwable testException = null;

        try {
            senderService.sendMms(testMMSRequest);
        } catch (Throwable exception) {
            testException = exception;
        }

        assertEquals(IllegalArgumentException.class, testException.getClass());
    }

    @Test
    void sendMMS_InvalidNumberWithCountryCodeWithSpaceValidMessageNonEmptyURL_ReturnIllegalArgumentException() {
        MmsRequest testMMSRequest = new MmsRequest("+65 9999 88888", "This is a test message","https://picsum.photos/200");

        Throwable testException = null;

        try {
            senderService.sendMms(testMMSRequest);
        } catch (Throwable exception) {
            testException = exception;
        }

        assertEquals(IllegalArgumentException.class, testException.getClass());
    }

    @Test
    void sendMMS_InvalidNumberWithoutCountryCodeWithoutSpaceValidMessageNonEmptyURL_ReturnIllegalArgumentException() {
        MmsRequest testMMSRequest = new MmsRequest("999988888", "This is a test message","https://picsum.photos/200");

        Throwable testException = null;

        try {
            senderService.sendMms(testMMSRequest);
        } catch (Throwable exception) {
            testException = exception;
        }

        assertEquals(IllegalArgumentException.class, testException.getClass());
    }

    @Test
    void sendMMS_InvalidNumberWithoutCountryCodeWithSpaceValidMessageNonEmptyURL_ReturnIllegalArgumentException() {
        MmsRequest testMMSRequest = new MmsRequest("9999 88888", "This is a test message","https://picsum.photos/200");

        Throwable testException = null;

        try {
            senderService.sendMms(testMMSRequest);
        } catch (Throwable exception) {
            testException = exception;
        }

        assertEquals(IllegalArgumentException.class, testException.getClass());
    }

    @Test
    void sendMMS_ValidNumberAboveMaxMessageLengthNonEmptyURL_ReturnAuthenticationException() {
        MmsRequest testMMSRequest = new MmsRequest("+65 9999 8888", "This is a test message that is so long and it exceeds the number of length and it is not a valid message! You should not sending this kind of message!","https://picsum.photos/200");

        Throwable testException = null;

        try {
            senderService.sendMms(testMMSRequest);
        } catch (Throwable exception) {
            testException = exception;
        }

        assertNotNull(testException);
    }

    @Test
    void sendMMS_ValidNumberBelowMinMessageLengthNonEmptyURL_ReturnAuthenticationException() {
        MmsRequest testMMSRequest = new MmsRequest("+65 9999 8888", "", "https://picsum.photos/200");

        Throwable testException = null;

        try {
            senderService.sendMms(testMMSRequest);
        } catch (Throwable exception) {
            testException = exception;
        }

        assertNotNull(testException);
    }

    @Test
    void sendMMS_InvalidNumberBelowMinMessageLengthNonEmptyURL_ReturnIllegalArgumentException() {
        MmsRequest testMMSRequest = new MmsRequest("+65 9999 88888", "", "https://picsum.photos/200");

        Throwable testException = null;

        try {
            senderService.sendMms(testMMSRequest);
        } catch (Throwable exception) {
            testException = exception;
        }

        assertEquals(IllegalArgumentException.class, testException.getClass());
    }

    @Test
    void sendMMS_InvalidNumberAboveMaxMessageLengthNonEmptyURL_ReturnIllegalArgumentException() {
        MmsRequest testMMSRequest = new MmsRequest("+65 9999 88888", "This is a test message that is so long and it exceeds the number of length and it is not a valid message!", "https://picsum.photos/200");

        Throwable testException = null;

        try {
            senderService.sendMms(testMMSRequest);
        } catch (Throwable exception) {
            testException = exception;
        }

        assertEquals(IllegalArgumentException.class, testException.getClass());
    }

    @Test
    void sendMMS_ValidNumberValidMessageEmptyURL_ReturnIllegalArgumentException() {
        MmsRequest testMMSRequest = new MmsRequest("+65 9999 8888", "This is a test message!", "");

        Throwable testException = null;

        try {
            senderService.sendMms(testMMSRequest);
        } catch (Throwable exception) {
            testException = exception;
        }

        assertNotNull(testException);
    }

}
