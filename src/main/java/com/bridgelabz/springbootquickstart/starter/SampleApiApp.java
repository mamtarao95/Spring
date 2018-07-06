package com.bridgelabz.springbootquickstart.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.bridgelabz.springbootquickstart.*"})
public class SampleApiApp {
	public static void main(String[] args) {
	SpringApplication.run(SampleApiApp.class, args); //static class springapplication with static runmethod
		
	}

}
