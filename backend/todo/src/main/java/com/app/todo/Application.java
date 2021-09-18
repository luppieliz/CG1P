package com.app.todo;

import com.app.todo.*;
import java.util.*;

import com.app.todo.measure.MeasureService;
import com.app.todo.user.User;
import com.app.todo.user.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class Application {
//	@Bean
//	BCryptPasswordEncoder bCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		MeasureService testService = ctx.getBean(MeasureService.class);
		List<String> sourcesLink = testService.getMeasures();
		sourcesLink.forEach(src -> System.out.println("Link scraped: " + src));

		UserService users = ctx.getBean(UserService.class);
//		BCryptPasswordEncoder encoder = ctx.getBean(BCryptPasswordEncoder.class);
		System.out.println("[Add user]: " + users.addUser(
				new User("admin", "admin@gmail.com", "goodpassword", "ROLE_ADMIN")).getUsername());
	}
}
