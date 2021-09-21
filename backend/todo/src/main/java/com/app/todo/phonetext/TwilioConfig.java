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
    private final String authToken = "575b0fcdea7f2052968d39b68486b233";
    private final String phoneNumber ="+13235534957";
}
