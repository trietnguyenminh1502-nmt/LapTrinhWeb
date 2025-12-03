package com.bt.bt06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.bt.bt06.model.Category;
import com.bt.bt06.model.User;
import com.bt.bt06.model.Video;
import com.bt.bt06.repository.CategoryRepository;
import com.bt.bt06.repository.UserRepository;
import com.bt.bt06.repository.VideoRepository;

@SpringBootApplication
public class Bt06Application {

	public static void main(String[] args) {
		SpringApplication.run(Bt06Application.class, args);
	}

	@Bean
	CommandLineRunner seedData(UserRepository userRepo, CategoryRepository categoryRepo, VideoRepository videoRepo,
			org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
		return args -> {
			// student user (non-admin)
			User u1 = new User();
			u1.setUsername("student1");
			u1.setFullname("Student One");
			u1.setPassword(passwordEncoder.encode("pass"));
			u1.setEmail("student1@example.com");
			u1.setAdmin(false);
			u1.setActive(true);
			userRepo.save(u1);

			// admin user for accessing admin pages
			User admin = new User();
			admin.setUsername("admin");
			admin.setFullname("Administrator");
			admin.setPassword(passwordEncoder.encode("adminpass"));
			admin.setEmail("admin@example.com");
			admin.setAdmin(true);
			admin.setActive(true);
			userRepo.save(admin);

			Category c1 = new Category();
			c1.setCategoryname("Tutorials");
			c1.setCategorycode("TUT");
			c1.setUser(u1);
			categoryRepo.save(c1);

			Video v1 = new Video();
			v1.setTitle("Spring Boot Basics");
			v1.setDescription("Intro to Spring Boot");
			v1.setViews(123);
			v1.setActive(true);
			v1.setCategory(c1);
			videoRepo.save(v1);
		};
	}

}
