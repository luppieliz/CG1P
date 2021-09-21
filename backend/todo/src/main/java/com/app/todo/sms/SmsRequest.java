package com.app.todo.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SmsRequest {
    @NotBlank
    @NotNull
    private String destPhoneNumber;

    @NotBlank
    @NotNull
    private String msg;

    public SmsRequest(@JsonProperty("phone_number") String destPhoneNumber,
                      @JsonProperty("msg") String msg) {
        this.destPhoneNumber = destPhoneNumber;
        this.msg = msg;
    }
}
