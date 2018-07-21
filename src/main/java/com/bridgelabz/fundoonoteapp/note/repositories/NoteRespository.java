package com.bridgelabz.fundoonoteapp.note.repositories;

import java.util.List;
import java.util.Optional;
import com.bridgelabz.fundoonoteapp.note.models.ViewNoteDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

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


}
