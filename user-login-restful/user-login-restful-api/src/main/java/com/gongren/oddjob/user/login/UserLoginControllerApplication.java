package com.gongren.oddjob.user.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = { "classpath:spring-root.xml" })
public class UserLoginControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserLoginControllerApplication.class, args);
	}
}
