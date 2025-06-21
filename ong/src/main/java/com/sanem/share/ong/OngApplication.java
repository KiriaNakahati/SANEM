package com.sanem.share.ong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OngApplication {

	public static void main(String[] args) {
		SpringApplication.run(OngApplication.class, args);
	}

}
