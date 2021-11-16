package com.app.buddy19.phonetext;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmsRequest that = (SmsRequest) o;
        return destPhoneNumber.equals(that.destPhoneNumber) && message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destPhoneNumber, message);
    }
}
