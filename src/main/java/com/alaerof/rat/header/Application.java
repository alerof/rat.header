package com.alaerof.rat.header;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		log.info("id: {}", UUID.randomUUID());
		SpringApplication.run(Application.class, args);
	}

}
