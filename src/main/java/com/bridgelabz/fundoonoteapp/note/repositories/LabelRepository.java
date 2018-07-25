package com.bridgelabz.fundoonoteapp.note.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoonoteapp.note.models.Label;

@Repository
public interface LabelRepository extends MongoRepository<Label,String> {

	List<Label> findAllByUserId(String userId);

	Optional<Label> findByLabelName(String labelName);


	Label findByLabelIdAndUserId(String labelId, String userId);

	void deleteByLabelId(String labelId);

	Optional<Label> findByLabelNameAndUserId(String labelName,String userId);
	
	


}
