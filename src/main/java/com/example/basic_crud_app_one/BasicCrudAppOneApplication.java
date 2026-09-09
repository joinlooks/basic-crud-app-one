package com.example.basic_crud_app_one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BasicCrudAppOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicCrudAppOneApplication.class, args);
	}
}
