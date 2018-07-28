package com.bridgelabz.fundoonoteapp.note.repositories;

import java.util.List;
import java.util.Optional;
import com.bridgelabz.fundoonoteapp.note.models.NoteDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonoteapp.note.models.Label;
import com.bridgelabz.fundoonoteapp.note.models.LabelDTO;
import com.bridgelabz.fundoonoteapp.note.models.Note;

@Repository
public interface NoteRespository extends MongoRepository<Note, String>{
	Optional<Note> findByNoteId(String noteId);

	Optional<Note> findByUserIdAndNoteId(String userId, String noteId);

	Long deleteNoteByIsTrashed(boolean trashed);

	Optional<Note> save(Optional<Note> note);
	
	void deleteNoteByIsTrashedAndUserId(boolean b, String id);

	Optional<Note> findOneByUserId(String userId);

	List<Note> findAllByUserIdAndIsTrashed(String id, boolean b);

	List<Note> findAllByUserIdAndIsArchive(String userId, boolean b);

	@Query(value = "{ 'userId' : ?0, 'labelList.labelId' : ?1 }")
    List<Note> findAllByQuery(String userId,String labelId);

	
}
