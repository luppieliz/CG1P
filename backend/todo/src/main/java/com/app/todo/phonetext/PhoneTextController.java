package com.app.todo.phonetext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path="api/v1/sender")
public class PhoneTextController {

    private final TwilioSenderService textSendingService;

    @Autowired
    public PhoneTextController(TwilioSenderService textSendingService) {
        this.textSendingService = textSendingService;
    }

    @PostMapping(path="/sms")
    public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
        textSendingService.sendSms(smsRequest);
    }

    @PostMapping(path="/mms")
    public void sendMms(@Valid @RequestBody MmsRequest MmsRequest) {
        textSendingService.sendMms(MmsRequest);
    }
}
