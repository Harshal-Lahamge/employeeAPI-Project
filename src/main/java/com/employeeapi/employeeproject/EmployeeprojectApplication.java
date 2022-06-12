package com.employeeapi.employeeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication //(exclude = {DataSourceAutoConfiguration.class})
public class EmployeeprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeprojectApplication.class, args);
	}

}
