package com.bridgelabz.springbootrest.service;

import java.util.Hashtable;
import com.bridgelabz.springbootrest.model.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
	Hashtable<String, Person> person=new Hashtable<String,Person>();

}
