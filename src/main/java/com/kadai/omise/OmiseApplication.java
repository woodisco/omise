package com.kadai.omise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class OmiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmiseApplication.class, args);
	}

}
