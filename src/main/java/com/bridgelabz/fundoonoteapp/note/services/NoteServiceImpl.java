package com.bridgelabz.fundoonoteapp.note.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonoteapp.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonoteapp.note.exceptions.UnAuthorizedException;
import com.bridgelabz.fundoonoteapp.note.models.CreateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.Note;
import com.bridgelabz.fundoonoteapp.note.models.UpdateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.ViewNoteDTO;
import com.bridgelabz.fundoonoteapp.note.repositories.NoteRespository;
import com.bridgelabz.fundoonoteapp.note.utility.Utility;
import com.bridgelabz.fundoonoteapp.user.repositories.UserRepository;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRespository noteRespository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ViewNoteDTO createNote(CreateNoteDTO createNoteDTO, String userId)
			throws UnAuthorizedException, NoteNotFoundException {
		Utility.validateCreateDTO(createNoteDTO);
		if (!userRepository.findById(userId).isPresent()) {
			throw new UnAuthorizedException("User not found");
		}
		Note note = modelMapper.map(createNoteDTO, Note.class);
		note.setCreatedAt(new Date());
		note.setUpdatedAt(new Date());
		note.setUserId(userId);
		noteRespository.save(note);
		return modelMapper.map(note, ViewNoteDTO.class);
	}

	@Override
	public void trashNote(String userId, String noteId) throws NoteNotFoundException, UnAuthorizedException {
		Optional<Note> optionalNote = validateNoteAndUser(noteId, userId);
		Note note = optionalNote.get();
		note.setTrashed(true);
		noteRespository.save(note);
	}

	@Override
	public void updateNote(UpdateNoteDTO updateNoteDTO, String userId)
			throws NoteNotFoundException, UnAuthorizedException {
		Optional<Note> optionalNote = validateNoteAndUser(updateNoteDTO.getNoteId(), userId);
		Note note = optionalNote.get();
		if (updateNoteDTO.getTitle() != null) {
			note.setTitle(updateNoteDTO.getTitle());
		}
		if (updateNoteDTO.getDescription() != null) {
			note.setDescription(updateNoteDTO.getDescription());
		}
		note.setUpdatedAt(new Date());
		noteRespository.save(note);
	}

	@Override
	public void deleteOrRestoreTrashedNote(String noteId, String userId,boolean isdelete) throws NoteNotFoundException, UnAuthorizedException {
		Optional<Note> optionalNote = validateNoteAndUser(noteId, userId);
		
		if (!optionalNote.get().isTrashed()) {
			throw new NoteNotFoundException("Note is not trashed yet");
		}
		if(isdelete) {
			noteRespository.deleteById(noteId);
		}
		else {
			Note note=optionalNote.get();
			note.setTrashed(false);
			noteRespository.save(note);
		}
		
	}

	@Override
	public void emptyTrash(String userId) throws NoteNotFoundException, UnAuthorizedException {
		if (!userRepository.findById(userId).isPresent()) {
			throw new UnAuthorizedException("User not found");
		}
		noteRespository.deleteNoteByIsTrashedAndUserId(true, userId);
	}

	@Override
	public void addReminder(String noteId, String userId, Date reminder)
			throws NoteNotFoundException, UnAuthorizedException {
		if (!Utility.validateReminder(reminder)) {
			throw new NoteNotFoundException("Reminder date is invalid");
		}
		Optional<Note> optionalNote = validateNoteAndUser(noteId, userId);
		Note note = optionalNote.get();
		note.setReminder(reminder);
		noteRespository.save(note);
	}

	@Override
	public void removeRemiander(String noteId, String userId) throws NoteNotFoundException, UnAuthorizedException {
		Optional<Note> optionalNote = validateNoteAndUser(noteId, userId);
		Note note = optionalNote.get();
		note.setReminder(null);
		noteRespository.save(note);

	}



	@Override
	public ViewNoteDTO viewNote(String noteId, String userId) throws NoteNotFoundException, UnAuthorizedException {
		Optional<Note> note = validateNoteAndUser(noteId, userId);
		if (note.get().isTrashed()) {
			throw new NoteNotFoundException(
					"Note with given id could not be found or note you are looking for might have been trashed");
		}
		return modelMapper.map(note.get(), ViewNoteDTO.class);

	}

	@Override
	public List<ViewNoteDTO> viewAllNotes(String userId) throws NoteNotFoundException {
		List<Note> note = noteRespository.findAllByUserIdAndIsTrashed(userId, false);
		if (note.isEmpty()) {
			throw new NoteNotFoundException("Notes are not available.kindly create note");
		}
		List<ViewNoteDTO> noteList = new ArrayList<>();
		for (int i = 0; i < note.size(); i++) {
			noteList.add(modelMapper.map(note.get(i), ViewNoteDTO.class));
		}

		return noteList;
	}

	private Optional<Note> validateNoteAndUser(String noteId, String userId)
			throws UnAuthorizedException, NoteNotFoundException {

		if (!userRepository.findById(userId).isPresent()) {
			throw new UnAuthorizedException("User is not found");
		}
		Optional<Note> optionalNote = noteRespository.findByNoteId(noteId);

		if (!optionalNote.isPresent()) {
			throw new NoteNotFoundException("Note was not found with the particular noteId");
		}
		if (!optionalNote.get().getUserId().equals(userId)) {
			throw new UnAuthorizedException("This particular note doesn't belongs to the user ");
		}
		return optionalNote;
	}

	@Override
	public Iterable<ViewNoteDTO> viewAllTrashedNotes(String userId) throws NoteNotFoundException {
		List<Note> note = noteRespository.findAllByUserIdAndIsTrashed(userId,true);
		if (note.isEmpty()) {
			throw new NoteNotFoundException("Notes are not available in trash");
		}
		List<ViewNoteDTO> noteList = new ArrayList<>();
		for (int i = 0; i < note.size(); i++) {
			noteList.add(modelMapper.map(note.get(i), ViewNoteDTO.class));
		}
		return noteList;
	}

	@Override
	public Iterable<ViewNoteDTO> geArchiveNotes(String userId) {
		
		return null;
	}


}
