package com.app.todo;

import com.app.todo.covid.DailyResponse;
import com.app.todo.measure.MeasureService;
import com.app.todo.user.User;
import com.app.todo.user.UserService;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException, InterruptedException, ParseException {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		MeasureService testService = ctx.getBean(MeasureService.class);
//		testService.getTag();
		List<String> sourcesLink = testService.getMeasures();
		sourcesLink.forEach(src -> System.out.println("Link scraped: " + src));

		UserService users = ctx.getBean(UserService.class);
		BCryptPasswordEncoder encoder = ctx.getBean(BCryptPasswordEncoder.class);
		System.out.println("[Add user]: " + users.addUser(
				new User("admin", "admin@gmail.com", encoder.encode("goodpassword"), "ROLE_ADMIN")).getUsername());

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://covid-19-data.p.rapidapi.com/country/code?code=sg"))
				.header("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
				.header("x-rapidapi-key", "af37c09083mshed16967800efcc8p130a91jsn8960686fa5d3")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());

		Gson gson = new Gson();
		DailyResponse[] objList = new Gson().fromJson(response.body(), DailyResponse[].class);

		for (DailyResponse dailyResponse: objList) {
			System.out.println(dailyResponse);
		}
	}
}
