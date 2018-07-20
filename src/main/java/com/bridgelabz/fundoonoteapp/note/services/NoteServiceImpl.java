package com.bridgelabz.fundoonoteapp.note.services;

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
	ModelMapper modelMapper;

	@Override
	public Note createNote(CreateNoteDTO createNoteDTO, String token)
			throws UnAuthorizedException, NoteNotFoundException {
		Utility.validateCreateDTO(createNoteDTO);
		String userId = Utility.parseJWT(token).getId();
		if (!userRepository.findById(userId).isPresent()) {
			throw new UnAuthorizedException("User not found");
		}
		Note note = modelMapper.map(createNoteDTO, Note.class);
		note.setCreatedAt(new Date());
		note.setUpdatedAt(new Date());
		note.setUserId(userId);
		noteRespository.save(note);
		return note;
	}

	@Override
	public void trashNote(String token, String noteId) throws NoteNotFoundException, UnAuthorizedException {
		Optional<Note> optionalNote = validateNoteAndUser(noteId, Utility.parseJWT(token).getId());
		Note note = modelMapper.map(optionalNote.get(), Note.class);
		note.setTrashed(true);
		noteRespository.save(note);
	}

	@Override
	public void updateNote(UpdateNoteDTO updateNoteDTO, String token)
			throws NoteNotFoundException, UnAuthorizedException {
		Utility.validateUpdateDTO(updateNoteDTO);
		Optional<Note> optionalNote = validateNoteAndUser(updateNoteDTO.getNoteId(), Utility.parseJWT(token).getId());
		if (updateNoteDTO.getTitle() != null) {
			optionalNote.get().setTitle(updateNoteDTO.getTitle());
		}
		if (updateNoteDTO.getDescription() != null) {
			optionalNote.get().setDescription(updateNoteDTO.getDescription());
		}
		Note note = modelMapper.map(optionalNote.get(), Note.class);
		note.setUpdatedAt(new Date());
		noteRespository.save(note);
	}

	@Override
	public void deletetrashedNote(String noteId, String token) throws NoteNotFoundException, UnAuthorizedException {
		Optional<Note> note = validateNoteAndUser(noteId, Utility.parseJWT(token).getId());
		if (!note.get().isTrashed()) {
			throw new NoteNotFoundException("Note is not trashed yet");
		}
		noteRespository.deleteById(noteId);
	}

	@Override
	public void emptyTrash(String token) throws NoteNotFoundException, UnAuthorizedException {
		if (!userRepository.findById(Utility.parseJWT(token).getId()).isPresent()) {
			throw new UnAuthorizedException("User not found");
		}
		noteRespository.deleteNoteByIsTrashedAndUserId(true, Utility.parseJWT(token).getId());
	}

	@Override
	public void addReminder(String noteId, String token, Date reminder)
			throws NoteNotFoundException, UnAuthorizedException {
		if (!Utility.validateReminder(reminder)) {
			throw new NoteNotFoundException("Reminder date is invalid");
		}
		Optional<Note> optionalNote = validateNoteAndUser(noteId, Utility.parseJWT(token).getId());
		Note note = modelMapper.map(optionalNote.get(), Note.class);
		note.setReminder(reminder);
		noteRespository.save(note);
	}

	@Override
	public void removeRemiander(String noteId, String token) throws NoteNotFoundException, UnAuthorizedException {
		Optional<Note> optionalNote = validateNoteAndUser(noteId, Utility.parseJWT(token).getId());
		Note note = modelMapper.map(optionalNote.get(), Note.class);
		note.setReminder(null);
		noteRespository.save(note);

	}

	@Override
	public ViewNoteDTO viewNote(String noteId, String token) throws NoteNotFoundException, UnAuthorizedException {
		Optional<Note> note = validateNoteAndUser(noteId, Utility.parseJWT(token).getId());
		if (note.get().isTrashed()) {
			throw new NoteNotFoundException(
					"Note with given id could not be found or note you are looking for might have been trashed");
		}
		return modelMapper.map(note.get(), ViewNoteDTO.class);

	}

	@Override
	public List<ViewNoteDTO> viewAllNotes(String token) throws NoteNotFoundException {
		List<ViewNoteDTO> note = noteRespository.findAllByUserIdAndIsTrashed(Utility.parseJWT(token).getId(), false);
		if (note.isEmpty()) {
			throw new NoteNotFoundException("Notes are not available.kindly create note");
		}
		return note;
	}

	private Optional<Note> validateNoteAndUser(String noteId, String userId)
			throws UnAuthorizedException, NoteNotFoundException {

		if (!userRepository.findById(userId).isPresent()) {
			throw new UnAuthorizedException("User is not found");
		}
		if (!noteRespository.findByNoteId(noteId).isPresent()) {
			throw new NoteNotFoundException("Note was not found with the particular noteId");
		}
		if (noteRespository.findByUserIdAndNoteId(userId, noteId) == null) {
			throw new UnAuthorizedException("This particular note doesn't belongs to the user ");
		}

		return noteRespository.findByNoteId(noteId);
	}
}
