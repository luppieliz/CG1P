package com.app.buddy19.phonetext;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/sender")
public class PhoneTextController {

    private final TwilioSenderService textSendingService;

    @Autowired
    public PhoneTextController(TwilioSenderService textSendingService) {
        this.textSendingService = textSendingService;
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully send messages"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "API errors")})

    @ApiOperation(value = "Send SMS")
    @PostMapping(path = "/sms", produces = "application/json")
    public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
        textSendingService.sendSms(smsRequest);
    }

    @ApiOperation(value = "Send MMS")
    @PostMapping(path = "/mms", produces = "application/json")
    public void sendMms(@Valid @RequestBody MmsRequest MmsRequest) {
        textSendingService.sendMms(MmsRequest);
    }
}
