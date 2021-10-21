package com.app.todo;

import com.app.todo.covid.DailyResponse;
import com.app.todo.measure.MeasureService;
import com.app.todo.user.User;
import com.app.todo.user.UserService;
import com.app.todo.industry.Industry;
import com.app.todo.industry.IndustryService;
import com.app.todo.business.Business;
import com.app.todo.business.BusinessService;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

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
		List<String> sourcesLink = testService.getMeasures();
		sourcesLink.forEach(src -> System.out.println("Link scraped: " + src));

		UserService users = ctx.getBean(UserService.class);
		System.out.println("[Add user]: " + users.addUser(
				new User("admin@gmail.com", "admin", "goodpassword", "ROLE_ADMIN")).getUsername());
		System.out.println("[Add user]: " + users.addUser(
				new User("alice@gmail.com", "Alice", "password1", "ROLE_BUSINESSOWNER")).getUsername());
		System.out.println("[Add user]: " + users.addUser(
				new User("ben@gmail.com", "Ben", "password2", "ROLE_EMPLOYEE")).getUsername());

		IndustryService industries = ctx.getBean(IndustryService.class);
		System.out.println("[Add industry]: " + industries.addIndustry(
				new Industry("Healthcare")).getName());
		System.out.println("[Add industry]: " + industries.addIndustry(
				new Industry("Arts and Culture")).getName());
		System.out.println("[Add industry]: " + industries.addIndustry(
				new Industry("Food and Beverage")).getName());

		BusinessService businesses = ctx.getBean(BusinessService.class);
		Industry industry = industries.getIndustry("Arts and Culture");
		System.out.println("[Add Business]: " + businesses.addBusiness(
				new Business("abcde77777", "singapore museum", industry)).getName());
		industry = industries.getIndustry("Food and Beverage");
		System.out.println("[Add Business]: " + businesses.addBusiness(
				new Business("dfgjj90", "macdonalds", industry)).getName());


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
