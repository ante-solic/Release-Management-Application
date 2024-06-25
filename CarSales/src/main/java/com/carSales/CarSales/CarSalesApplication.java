package com.carSales.CarSales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class CarSalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarSalesApplication.class, args);
	}

}
