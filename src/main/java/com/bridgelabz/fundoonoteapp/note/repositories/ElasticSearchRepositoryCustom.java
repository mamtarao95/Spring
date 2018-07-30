package com.bridgelabz.fundoonoteapp.note.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonoteapp.note.models.Note;

@Repository
public interface ElasticSearchRepositoryCustom extends ElasticsearchRepository<Note,String>{

	Optional<Note> findById(String noteId);

	Optional<Note> findByTitle(String noteId);
	List<Note> findAllByUserIdAndIsTrashedFalse(String id);

	void deleteNoteByIsTrashedAndUserId(boolean b, String userId);

	List<Note> findAllByUserIdAndIsArchive(String userId, boolean b);
}
