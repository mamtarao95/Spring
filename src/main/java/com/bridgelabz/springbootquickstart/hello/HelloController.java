package com.bridgelabz.springbootquickstart.hello;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//spring mvc annotaion
// we can have methods which maps to a certain url
@RestController 	
@EnableAutoConfiguration
public class HelloController {

	@RequestMapping("/first")   // onlygor GET http method (default)
	public String sayHi() {
		return "Hello mamta";
	}

}
