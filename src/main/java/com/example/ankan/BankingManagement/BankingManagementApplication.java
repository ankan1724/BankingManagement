package com.example.ankan.BankingManagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "Banking management", version = "2023.8.R01",
		description = "This is a banking management application protected by spring security 6 using JWT token system",
		contact=@Contact(email = "the.ankan17@gmail.com",name = "Ankan Ghosh")),
		security = {@SecurityRequirement(name = "bearerToken")})
@SecurityScheme(name = "bearerToken",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		in = SecuritySchemeIn.HEADER,
		bearerFormat = "JWT")
public class BankingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingManagementApplication.class, args);
	}

}