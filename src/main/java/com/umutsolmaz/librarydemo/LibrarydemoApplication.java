package com.umutsolmaz.librarydemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibrarydemoApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(LibrarydemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LibrarydemoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		LOG.info("Library Demo Application starts...");

	}

}
