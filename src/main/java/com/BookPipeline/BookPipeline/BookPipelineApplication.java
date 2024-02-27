package com.BookPipeline.BookPipeline;

import com.BookPipeline.BookPipeline.login.model.Roles;
import com.BookPipeline.BookPipeline.login.model.UserEntity;
import com.BookPipeline.BookPipeline.login.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BookPipelineApplication {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

    public BookPipelineApplication(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
		SpringApplication.run(BookPipelineApplication.class, args);
	}

	@Bean
	CommandLineRunner createAdminUser(UserService userService, PasswordEncoder passwordEncoder) {
		return args -> {
			String adminUsername = "admin";
			if (!userService.existsByUsername(adminUsername)) {
				UserEntity admin = new UserEntity();
				admin.setUsername(adminUsername);
				admin.setPassword(passwordEncoder.encode("admin"));
				admin.setRole(Roles.ROLE_ADMIN);
				userService.save(admin);
			}
		};
	}

}
