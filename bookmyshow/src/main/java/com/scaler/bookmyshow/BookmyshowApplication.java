package com.scaler.bookmyshow;

import com.scaler.bookmyshow.controller.UserController;
import com.scaler.bookmyshow.dtos.SignUpRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookmyshowApplication implements CommandLineRunner {

	@Autowired
	private UserController userController;

	@Override
	public void run(String... args) throws Exception {
		SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
		signUpRequestDto.setEmail("varun@scaler.com");
		signUpRequestDto.setPassword("password");
		userController.signUp(signUpRequestDto);

	}

	public static void main(String[] args) {

		SpringApplication.run(BookmyshowApplication.class, args);

	}

}
