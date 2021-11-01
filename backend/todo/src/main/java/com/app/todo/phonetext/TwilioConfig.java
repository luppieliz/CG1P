package com.app.todo.phonetext;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
@Setter
@Getter
public class TwilioConfig {
    private final String accountSID = "AC5284a3699423deebf5c5a38b12658777";
    private final String authToken = "3b1c13c4212513788eeaff1f9d7cac12";
    private final String phoneNumber ="+13235534957";

    public String getAccountSID() {
        return accountSID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
