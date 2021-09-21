package com.app.todo.phonetext;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MmsRequest {
    @NotBlank
    @NotNull
    private String destPhoneNumber;

    @NotBlank
    @NotNull
    private String msg;

    @NotNull
    private String imageURL;

    public MmsRequest(@JsonProperty("phone_number") String destPhoneNumber,
                      @JsonProperty("msg") String msg,
    @JsonProperty("url") String imageURL) {
        this.destPhoneNumber = destPhoneNumber;
        this.msg = msg;
        this.imageURL = imageURL;
    }
}
