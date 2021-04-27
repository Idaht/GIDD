package idatt2106.group3.backend;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import idatt2106.group3.backend.Component.EmailComponent;
import idatt2106.group3.backend.Enum.Difficulty;
import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Model.DTO.User.UserWithPasswordDTO;
import idatt2106.group3.backend.Repository.ActivityRepository;
import idatt2106.group3.backend.Repository.UserRepository;
import idatt2106.group3.backend.Service.UserService;

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

	// @Autowired
	// private UserRepository userRepo;
	// @Autowired
	// private UserService userService;

	// @Autowired
	// private ActivityRepository acitivityRepo;
	// @Bean
	// public CommandLineRunner run(){
	// 	return args -> {
	// 		UserWithPasswordDTO user = new UserWithPasswordDTO("test","test","test@test.com", LocalDate.of(2000, 1, 1), Difficulty.EASY, "hash",null);
	// 		userService.createUser(user);
	// 		Activity acitivty = new Activity("title", "type", "desc","equip", Difficulty.EASY.value,"bergen","place",1.2,1.2,LocalDateTime.now(),123,false, 10, false, null);
	// 		acitivityRepo.save(acitivty);
	// 		Activity activity = acitivityRepo.findById(1L).get();
	// 		User user1 = userRepo.findById(1L).get();
	// 		user1.getActivities().add(activity);
	// 		userRepo.save(user1);
	// 	};
	// }
}	
