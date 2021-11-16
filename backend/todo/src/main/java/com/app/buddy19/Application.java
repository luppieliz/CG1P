package com.app.buddy19;

import com.app.buddy19.business.BusinessServiceImpl;
import com.app.buddy19.covid.DailyResponse;
import com.app.buddy19.industry.IndustryServiceImpl;
import com.app.buddy19.scheduler.ScheduledTasks;
import com.app.buddy19.user.User;
import com.app.buddy19.user.UserService;
import com.app.buddy19.industry.Industry;
import com.app.buddy19.industry.IndustryService;
import com.app.buddy19.business.Business;
import com.app.buddy19.business.BusinessService;

import com.app.buddy19.user.UserServiceImpl;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;

@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) throws IOException, InterruptedException, ParseException {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);



		IndustryService industries = ctx.getBean(IndustryServiceImpl.class);
		System.out.println("[Add industry]: " + industries.addIndustry(
				new Industry("Healthcare")).getName());
		System.out.println("[Add industry]: " + industries.addIndustry(
				new Industry("F&B")).getName());
		System.out.println("[Add industry]: " + industries.addIndustry(
				new Industry("Tourism")).getName());
		System.out.println("[Add industry]: " + industries.addIndustry(
				new Industry("Retail")).getName());

		BusinessService businesses = ctx.getBean(BusinessServiceImpl.class);
		Industry industry = industries.getIndustry("Tourism");
		System.out.println("[Add Business]: " + businesses.addBusiness(
				new Business("abcde77777", "singapore museum", industry)).getName());
		industry = industries.getIndustry("F&B");
		System.out.println("[Add Business]: " + businesses.addBusiness(
				new Business("dfgjj90", "macdonalds", industry)).getName());

		UserService users = ctx.getBean(UserServiceImpl.class);
		Business business = businesses.getBusiness("abcde77777");
		System.out.println("[Add user]: " + users.addUser(
				new User("admin@gmail.com", "admin", "goodpassword", "ROLE_ADMIN", business)).getUsername());
		System.out.println("[Add user]: " + users.addUser(
				new User("alice@gmail.com", "Alice", "password1", "ROLE_BUSINESSOWNER", business)).getUsername());
		System.out.println("[Add user]: " + users.addUser(
				new User("kornflakesgoh@gmail.com", "Marcus", "password0", "ROLE_EMPLOYEE", business)).getUsername());
		business = businesses.getBusiness("dfgjj90");
		System.out.println("[Add user]: " + users.addUser(
				new User("ben@gmail.com", "Ben", "password2", "ROLE_EMPLOYEE", business)).getUsername());

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

		//Startup tasks
		ScheduledTasks scheduledTasks = ctx.getBean(ScheduledTasks.class);
		scheduledTasks.getNewsOnSchedule();
		scheduledTasks.getFAQUpdatesOnSchedule();
	}
}
