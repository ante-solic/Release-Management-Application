package com.asolic.ReleaseManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class ReleaseManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReleaseManagementApplication.class, args);
	}

}
