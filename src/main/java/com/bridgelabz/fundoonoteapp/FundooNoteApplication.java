package com.bridgelabz.fundoonoteapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/*@EnableElasticsearchRepositories("com.bridgelabz.fundoonoteapp.note")
@EnableMongoRepositories("com.bridgelabz.fundoonoteapp.note.repositories")*/

//@EnableMongoRepositories("com.bridgelabz.fundoonoteapp.note.repositories")
//@EnableElasticsearchRepositories("com.bridgelabz.fundoonoteapp.note")
@SpringBootApplication
public class FundooNoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooNoteApplication.class, args);
		
	}

}


