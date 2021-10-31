package com.app.todo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository users;

    @Autowired
    public CustomUserDetailsService(UserRepository users) {
        this.users = users;
    }

    @Override
    /**
     * To return a UserDetails for Spring Security Note that the method takes only
     * email. The UserDetails interface has methods to get the password.
     */
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return users.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email '" + email + "' not found"));
    }
}