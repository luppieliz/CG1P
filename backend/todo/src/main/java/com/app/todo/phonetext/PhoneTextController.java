package com.app.todo.phonetext;

import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Send SMS")
    @PostMapping(path="/sms", produces = "application/json")
    public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
        textSendingService.sendSms(smsRequest);
    }

    @ApiOperation(value = "Send MMS")
    @PostMapping(path="/mms", produces = "application/json")
    public void sendMms(@Valid @RequestBody MmsRequest MmsRequest) {
        textSendingService.sendMms(MmsRequest);
    }
}
