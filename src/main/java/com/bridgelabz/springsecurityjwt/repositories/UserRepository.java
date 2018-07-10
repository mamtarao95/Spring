package com.bridgelabz.springsecurityjwt.repositories;


import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.springsecurityjwt.models.User;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
	
	 public Optional<User> findByUserName(String userName);
	 public Optional<User> findByEmail(String email);
	
	 
}
