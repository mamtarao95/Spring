package com.bridgelabz.fundoonoteapp.note.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonoteapp.note.exceptions.NoteException;
import com.bridgelabz.fundoonoteapp.note.models.CreateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.Note;
import com.bridgelabz.fundoonoteapp.note.models.UpdateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.ViewNoteDTO;
import com.bridgelabz.fundoonoteapp.note.repositories.NoteRespository;
import com.bridgelabz.fundoonoteapp.note.utility.Utility;

import io.jsonwebtoken.Claims;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRespository noteRespository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public void createNote(CreateNoteDTO createNoteDTO, String token) throws NoteException {
		Utility.validateCreateDTO(createNoteDTO);
		Claims claims = Utility.parseJWT(token);
		Note note = modelMapper.map(createNoteDTO, Note.class);
		note.setCreatedAt(new Date());
		note.setUpdatedAt(new Date());
		note.setUserId(claims.getId());
		noteRespository.save(note);
	}

	@Override
	public void trashNote(String token, String noteId) throws NoteException {
		Claims claims = Utility.parseJWT(token);
		Note note = noteRespository.findByUserIdAndNoteId(claims.getId(), noteId);
		if (note == null) {
			throw new NoteException("Note with the given id could not be found");
		}
		note.setTrashed(true);
		noteRespository.save(note);

	}

	@Override
	public void updateNote(UpdateNoteDTO updateNoteDTO, String token, String noteId) throws NoteException {
		Utility.validateUpdateDTO(updateNoteDTO);
		Claims claims = Utility.parseJWT(token);
		Note note = noteRespository.findByUserIdAndNoteId(claims.getId(), noteId);
		if (note == null || note.isTrashed()) {
			throw new NoteException("Note with the given id could not be found or note might have been trashed");
		}
		note.setTitle(updateNoteDTO.getTitle());
		note.setDescription(updateNoteDTO.getDescription());
		note.setUpdatedAt(new Date());
		noteRespository.save(note);
	}

	@Override
	public void deletetrashedNote(String noteId) throws NoteException {
		Optional<Note> note = noteRespository.findByNoteId(noteId);
		if(!note.isPresent()) {
			throw new NoteException("Note is not found");
		}
		if (!note.get().isTrashed()) {
			throw new NoteException("Note is not trashed yet");
		}
		noteRespository.deleteById(noteId);
	}

	@Override
	public void emptyTrash() throws NoteException {
		noteRespository.deleteNoteByIsTrashed(true);
	}

	@Override
	public void addReminder(String noteId, String token, Date reminder) throws NoteException {
		if (!Utility.validateReminder(reminder)) {
			throw new NoteException("Reminder date is invalid");
		}
		Claims claims = Utility.parseJWT(token);
		Note note = noteRespository.findByUserIdAndNoteId(claims.getId(), noteId);
		if (note == null) {
			throw new NoteException("Note with the given id could not be found");
		}
		note.setReminder(reminder);
		noteRespository.save(note);
	}

	@Override
	public void removeRemiander(String noteId, String token) throws NoteException {
		Claims claims = Utility.parseJWT(token);
		Note note = noteRespository.findByUserIdAndNoteId(claims.getId(), noteId);
		if (note == null) {
			throw new NoteException("Note with the given id could not be found");
		}
		note.setReminder(null);
		noteRespository.save(note);

	}

	@Override
	public ViewNoteDTO viewNote(String noteId) throws NoteException {
		Optional<Note> note = noteRespository.findById(noteId);
		if (!note.isPresent()) {
			throw new NoteException("Note with given id could not be found");
		}
		return modelMapper.map(note.get(), ViewNoteDTO.class);

	}

	@Override
	public List<ViewNoteDTO> viewAllNotes(String token) {
		return noteRespository.findAllByUserId(Utility.parseJWT(token).getId());

	}

}
