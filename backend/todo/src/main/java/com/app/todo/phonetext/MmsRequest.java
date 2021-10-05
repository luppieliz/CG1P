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
    private String message;

    @NotNull
    private String imageURL;

    public MmsRequest() {}

    public MmsRequest(@JsonProperty("phone_number") String destPhoneNumber,
                      @JsonProperty("message") String message,
    @JsonProperty("url") String imageURL) {
        this.destPhoneNumber = destPhoneNumber;
        this.message = message;
        this.imageURL = imageURL;
    }


}
