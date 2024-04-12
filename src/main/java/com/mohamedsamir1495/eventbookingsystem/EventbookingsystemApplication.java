package com.mohamedsamir1495.eventbookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class EventbookingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventbookingsystemApplication.class, args);
	}

}
