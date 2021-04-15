package idatt2106.group3.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Repository.UserRepository;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}


	// @Autowired
	// private UserRepository userRepo;
	// @Bean
	// public CommandLineRunner run(){
	// 	return args -> {
	// 		User user = new User("diderikk","Diderik","Kramer","test@gmail.com","123","231",123,123,"123",123);

	// 		userRepo.save(user);
	// 	};
	// }
}
