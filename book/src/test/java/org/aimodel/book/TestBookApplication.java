package org.aimodel.book;

import org.springframework.boot.SpringApplication;

public class TestBookApplication {

	public static void main(String[] args) {
		SpringApplication.from(BookApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
