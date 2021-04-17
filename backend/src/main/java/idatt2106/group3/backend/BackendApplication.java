package idatt2106.group3.backend;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.Difficulty;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Repository.ActivityRepository;
import idatt2106.group3.backend.Repository.UserRepository;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		// Generates the keystore using the makefile
		try {
			Runtime.getRuntime().exec("make keystore");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Runs the spring-boot application
		SpringApplication.run(BackendApplication.class, args);
	}

	/**
	 * Allows CORS to frontend
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**").allowedOrigins("http://localhost:3000");
			}
		};
	}

	// @Autowired
	// private UserRepository userRepo;

	// @Autowired
	// private ActivityRepository acitivityRepo;
	// @Bean
	// public CommandLineRunner run(){
	// 	return args -> {
	// 		User user = new User("test","test","test@test.com","hash","salt",123,123,"role",1);
	// 		userRepo.save(user);
	// 		Activity acitivty = new Activity("desc","equip", Difficulty.EASY,"bergen","place",1.2,1.2,LocalDateTime.now(),123,false);
	// 		acitivityRepo.save(acitivty);
	// 		Activity activity = acitivityRepo.findById(1L).get();
	// 		User user1 = userRepo.findById(1L).get();
	// 		user1.getActivities().add(activity);
	// 		userRepo.save(user1);

	// 	};
	// }
}
