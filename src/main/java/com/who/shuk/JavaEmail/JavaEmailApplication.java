package com.who.shuk.JavaEmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JavaEmailApplication {
	public static void main(String[] args) {
		SpringApplication.run(JavaEmailApplication.class, args);
	}
}
