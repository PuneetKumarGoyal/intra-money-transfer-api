package com.mastercard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.mastercard.repository")
public class IntraMoneyTransferApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntraMoneyTransferApiApplication.class, args);
	}

}
