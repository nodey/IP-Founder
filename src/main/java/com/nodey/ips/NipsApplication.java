package com.nodey.ips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NipsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NipsApplication.class, args);
	}

}
