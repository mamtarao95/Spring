package com.bridgelabz.fundoonoteapp.note.repositories;

import java.util.List;
import java.util.Optional;
import com.bridgelabz.fundoonoteapp.note.models.NoteViewDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonoteapp.note.models.Label;
import com.bridgelabz.fundoonoteapp.note.models.LabelViewDTO;
import com.bridgelabz.fundoonoteapp.note.models.Note;

@Repository
public interface NoteRespository extends MongoRepository<Note, String> {
	Optional<Note> findByNoteId(String noteId);

	Note findByUserIdAndNoteId(String UserId, String noteId);

	Long deleteNoteByIsTrashed(boolean trashed);

	void save(Optional<Note> note);
	void deleteNoteByIsTrashedAndUserId(boolean b, String id);

	Optional<Note> findOneByUserId(String userId);

	List<Note> findAllByUserIdAndIsTrashed(String id, boolean b);

	List<Note> findAllByUserIdAndIsArchive(String userId, boolean b);

	@Query(value = "{ 'userId' : ?0, 'labelList.labelName' : ?1 }", fields = "{ 'labelList.labelName' : 1 }")
    Label findByQuery(String userId, String labelName);
}
