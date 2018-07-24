package com.bridgelabz.fundoonoteapp.note.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonoteapp.note.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonoteapp.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonoteapp.note.exceptions.UnAuthorizedException;
import com.bridgelabz.fundoonoteapp.note.models.CreateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.Label;
import com.bridgelabz.fundoonoteapp.note.models.LabelViewDTO;
import com.bridgelabz.fundoonoteapp.note.models.Note;
import com.bridgelabz.fundoonoteapp.note.models.UpdateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.NoteViewDTO;
import com.bridgelabz.fundoonoteapp.note.repositories.LabelRepository;
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

	@Autowired
	LabelRepository labelRepository;

	@Override
	public NoteViewDTO createNote(CreateNoteDTO createNoteDTO, String userId)
			throws UnAuthorizedException, NoteNotFoundException, LabelNotFoundException {

		Utility.validateCreateDTO(createNoteDTO);

		if (!userRepository.findById(userId).isPresent()) {
			throw new UnAuthorizedException("User not found");
		}

		Note note = modelMapper.map(createNoteDTO, Note.class);
		/*
		 * if (createNoteDTO.getLabel() != null) { LabelViewDTO labelViewDTO =
		 * createLabel(createNoteDTO.getLabel(), userId); List<Label> labelList = new
		 * ArrayList<>(); labelList.add(modelMapper.map(labelViewDTO, Label.class));
		 * note.setLabelList(labelList); System.out.println(note.getLabelList().get(0));
		 * }
		 */
		note.setCreatedAt(new Date());
		note.setUpdatedAt(new Date());
		note.setUserId(userId);
		noteRespository.save(note);
		return modelMapper.map(note, NoteViewDTO.class);
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
	public void deleteOrRestoreTrashedNote(String noteId, String userId, boolean isdelete)
			throws NoteNotFoundException, UnAuthorizedException {
		Optional<Note> optionalNote = validateNoteAndUser(noteId, userId);

		if (!optionalNote.get().isTrashed()) {
			throw new NoteNotFoundException("Note is not trashed yet");
		}
		if (isdelete) {
			noteRespository.deleteById(noteId);
		} else {
			Note note = optionalNote.get();
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
	public NoteViewDTO viewNote(String noteId, String userId) throws NoteNotFoundException, UnAuthorizedException {
		Optional<Note> note = validateNoteAndUser(noteId, userId);
		if (note.get().isTrashed()) {
			throw new NoteNotFoundException(
					"Note with given id could not be found or note you are looking for might have been trashed");
		}
		return modelMapper.map(note.get(), NoteViewDTO.class);

	}

	@Override
	public List<NoteViewDTO> viewAllNotes(String userId) throws NoteNotFoundException {
		List<Note> note = noteRespository.findAllByUserIdAndIsTrashed(userId, false);
		if (note.isEmpty()) {
			throw new NoteNotFoundException("Notes are not available.kindly create note");
		}
		List<NoteViewDTO> noteList = new ArrayList<>();
		for (int i = 0; i < note.size(); i++) {
			noteList.add(modelMapper.map(note.get(i), NoteViewDTO.class));
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
	public Iterable<NoteViewDTO> viewAllTrashedNotes(String userId) throws NoteNotFoundException {
		List<Note> note = noteRespository.findAllByUserIdAndIsTrashed(userId, true);
		if (note.isEmpty()) {
			throw new NoteNotFoundException("Notes are not available in trash");
		}
		List<NoteViewDTO> noteList = new ArrayList<>();
		for (int i = 0; i < note.size(); i++) {
			noteList.add(modelMapper.map(note.get(i), NoteViewDTO.class));
		}
		return noteList;
	}

	@Override
	public Iterable<NoteViewDTO> getArchiveNotes(String userId) throws NoteNotFoundException {
		List<Note> note = noteRespository.findAllByUserIdAndIsArchive(userId, true);
		if (note.isEmpty()) {
			throw new NoteNotFoundException("Notes are not available");
		}
		List<NoteViewDTO> noteList = new ArrayList<>();
		for (int i = 0; i < note.size(); i++) {
			noteList.add(modelMapper.map(note.get(i), NoteViewDTO.class));
		}
		return noteList;

	}

	@Override
	public void setArchive(String userId, String noteId) throws NoteNotFoundException, UnAuthorizedException {
		Optional<Note> optionalNote = validateNoteAndUser(noteId, userId);
		Note note = optionalNote.get();
		note.setArchive(true);
		noteRespository.save(note);

	}

	@Override
	public void unArchive(String userId, String noteId) throws UnAuthorizedException, NoteNotFoundException {
		Optional<Note> optionalNote = validateNoteAndUser(noteId, userId);
		Note note = optionalNote.get();
		note.setArchive(false);
		noteRespository.save(note);
	}

	@Override
	public void pinNote(String userId, String noteId) throws UnAuthorizedException, NoteNotFoundException {
		Optional<Note> optionalNote = validateNoteAndUser(noteId, userId);
		Note note = optionalNote.get();
		note.setPin(true);
		noteRespository.save(note);
	}

	@Override
	public void unPinNote(String userId, String noteId) throws UnAuthorizedException, NoteNotFoundException {
		Optional<Note> optionalNote = validateNoteAndUser(noteId, userId);
		Note note = optionalNote.get();
		note.setPin(false);
		noteRespository.save(note);

	}

	@Override
	public List<LabelViewDTO> getLabels(String userId) throws LabelNotFoundException {
		List<Label> labelList = labelRepository.findAllByUserId(userId);
		if (labelList.isEmpty()) {
			throw new LabelNotFoundException("Labels are not available");
		}
		List<LabelViewDTO> labelListView = new ArrayList<>();
		for (int i = 0; i < labelList.size(); i++) {
			labelListView.add(modelMapper.map(labelList.get(i), LabelViewDTO.class));
		}
		return labelListView;
	}

	@Override
	public LabelViewDTO createLabel(String labelName, String userId)
			throws UnAuthorizedException, LabelNotFoundException {
		if (!userRepository.findById(userId).isPresent()) {
			throw new UnAuthorizedException("User not found");
		}
		Label labelFound = labelRepository.findByLabelNameAndUserId(labelName, userId);
		if (labelFound != null) {
			return modelMapper.map(labelFound, LabelViewDTO.class);
		}
		Label label = new Label();
		label.setLabelName(labelName);
		label.setUserId(userId);
		labelRepository.save(label);
		return modelMapper.map(label, LabelViewDTO.class);

	}

	@Override
	public void deleteLabel(String labelName, String userId) throws UnAuthorizedException, LabelNotFoundException {
		if (!userRepository.findById(userId).isPresent()) {
			throw new UnAuthorizedException("User not found");
		}
		Label labelFound = labelRepository.findByLabelNameAndUserId(labelName, userId);
		if (labelFound == null) {
			throw new LabelNotFoundException("Label not found");
		}
		
		Label label=noteRespository.findByQuery(userId, labelName);
		System.out.println(label.getLabelName());
		/*
		 * List<String> noteIds=labelFound.getNoteId(); for(int i=0 ;i <
		 * noteIds.size();i++) {
		 * noteRespository.findByUserIdAndNoteId(userId,noteIds.get(i)); }
		 * 
		 * labelRepository.delete(labelFound);
		 */
		//labelRepository.delete(labelFound);

	}

	@Override
	public LabelViewDTO renameLabel(String labelName, String userId) {

		return null;
	}

	@Override
	public Iterable<LabelViewDTO> getNotesOfLabel(String labelName, String userId)
			throws UnAuthorizedException, LabelNotFoundException {
		if (!userRepository.findById(userId).isPresent()) {
			throw new UnAuthorizedException("User not found");
		}
		Label labelFound = labelRepository.findByLabelNameAndUserId(labelName, userId);
		if (labelFound == null) {
			throw new LabelNotFoundException("Label not found");
		}
		//return noteRespository.findByUserIdAndLabelListLabelName(userId, labelName);
		return null;

	}

	@Override
	public void addLabel(String labelName, String userId, String noteId)
			throws UnAuthorizedException, NoteNotFoundException, LabelNotFoundException {
		Optional<Note> note = validateNoteAndUser(noteId, userId);

		if (note.get().getLabelList() == null) {
			List<Label> newLabelList = new ArrayList<>();
			note.get().setLabelList(newLabelList);
		}

		Label labelFound = labelRepository.findByLabelNameAndUserId(labelName, userId);
		Label label = new Label();

		if (labelFound == null) {
			LabelViewDTO labelViewDTO = createLabel(labelName, userId);
			label.setLabelId(labelViewDTO.getLabelId());
			label.setLabelName(labelName);
			note.get().getLabelList().add(label);
		} else {

			for (int i = 0; i < note.get().getLabelList().size(); i++) {
				if (note.get().getLabelList().get(i).getLabelId().equals(labelFound.getLabelId())) {
					throw new LabelNotFoundException("Label already present");
				}
			}

			label.setLabelId(labelFound.getLabelId());
			label.setLabelName(labelName);
			note.get().getLabelList().add(label);
		}
		noteRespository.save(note.get());

	}
}
