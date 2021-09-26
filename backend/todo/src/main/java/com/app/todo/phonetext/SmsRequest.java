package com.app.todo.phonetext;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String message;

    public SmsRequest() {}

    public SmsRequest(@JsonProperty("phone_number") String destPhoneNumber,
                      @JsonProperty("message") String message) {
        this.destPhoneNumber = destPhoneNumber;
        this.message = message;
    }
}
