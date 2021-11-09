package com.app.todo.security;

import com.app.todo.user.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
            authBuilder.userDetailsService(userDetailsService)
                       .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()

                    // for TodoController
                    .antMatchers(HttpMethod.GET, "/**/todos", "/**/todos/**").authenticated()
                    .antMatchers(HttpMethod.DELETE, "/**/todos/**").authenticated()
                    .antMatchers(HttpMethod.PUT, "/**/todos/**").authenticated()
                    .antMatchers(HttpMethod.POST, "/**/todos").authenticated()

                    // for UserController
                    .antMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/user/**").hasRole("ADMIN")

                    // for MeasureController
                    .antMatchers(HttpMethod.GET, "/api/v1/industryMeasures").authenticated()

                    // for PhoneTextController
                    .antMatchers(HttpMethod.POST, "/sms").authenticated()
                    .antMatchers(HttpMethod.POST, "/mms").authenticated()

                    // for HellowWorld (Not working!)
                    .antMatchers(HttpMethod.GET, "/hello-world/**").hasAnyRole("ADMIN", "BUSINESSOWNER")

                .and()
                .csrf().disable()
                .formLogin().disable();

        //enable h2 console
        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/h2-console/**").permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
