package com.bridgelabz.fundoonoteapp.note.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoonoteapp.note.models.Label;

@Repository
public interface LabelRepository extends MongoRepository<Label,String> {

	List<Label> findAllByUserId(String userId);

	boolean findByLabelName(String labelName);

	Label findByLabelNameAndUserId(String labelName, String userId);
	
	


}
