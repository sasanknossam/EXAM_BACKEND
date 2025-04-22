package com.esa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.esa")
public class ExamSeatingArrangementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamSeatingArrangementApplication.class, args);
	}

}
