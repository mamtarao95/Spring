package com.bridgelabz.fundoonoteapp.note.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonoteapp.note.exceptions.LabelNameAlreadyUsedException;
import com.bridgelabz.fundoonoteapp.note.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonoteapp.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonoteapp.note.exceptions.NoteNotTrashedException;
import com.bridgelabz.fundoonoteapp.note.exceptions.ReminderDateNotValidException;
import com.bridgelabz.fundoonoteapp.note.exceptions.UnAuthorizedException;
import com.bridgelabz.fundoonoteapp.note.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonoteapp.note.models.CreateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.Label;
import com.bridgelabz.fundoonoteapp.note.models.LabelDTO;
import com.bridgelabz.fundoonoteapp.note.models.Note;
import com.bridgelabz.fundoonoteapp.note.models.UpdateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.NoteDTO;
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
	public NoteDTO createNote(CreateNoteDTO createNoteDTO, String userId)
			throws UnAuthorizedException, NoteNotFoundException, LabelNotFoundException, ReminderDateNotValidException {

		Utility.validateCreateDTO(createNoteDTO);

		if (!userRepository.findById(userId).isPresent()) {
			throw new UnAuthorizedException("User not found");
		}
		List<LabelDTO> newlabelList = new ArrayList<>();
		if (createNoteDTO.getLabelNameList() != null) {

			List<String> labelList = createNoteDTO.getLabelNameList();
			for (int i = 0; i < labelList.size(); i++) {
				if (labelList.get(i).trim().length() != 0 || labelList.get(i) != null) {
					Optional<Label> labelFound = labelRepository.findByLabelNameAndUserId(labelList.get(i), userId);
					if (!labelFound.isPresent()) {
						throw new UnAuthorizedException("Label does not belongs to user");
					}

					LabelDTO label = new LabelDTO();
					label.setLabelId(labelFound.get().getLabelId());
					label.setLabelName(labelList.get(i));
					newlabelList.add(label);

				}
			}

		}
		Note note = new Note();
		
		if(createNoteDTO.getColor()==null || createNoteDTO.getColor().trim().equals("")) {
		note.setColor("white");
		}else {
			note.setColor(createNoteDTO.getColor());
		}
		
		if (createNoteDTO.getReminder() != null) {
			if (Utility.validateReminder(createNoteDTO.getReminder())) {
				throw new ReminderDateNotValidException("Reminder Date is not valid");
			}
			note.setReminder(createNoteDTO.getReminder());

		}
		
		if (createNoteDTO.isArchive()) {
			note.setArchive(true);
		}
		
		if (createNoteDTO.isPin()) {
			note.setPin(true);
		}
		Date date = new Date();
		note.setTitle(createNoteDTO.getTitle());
		note.setDescription(createNoteDTO.getDescription());
		note.setCreatedAt(date);
		note.setUpdatedAt(date);
		note.setUserId(userId);
		note.setLabels(newlabelList);
		Note optionalNote=noteRespository.save(note);	
		
		return mapModels(optionalNote);		//change to convertor
		

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
			throws UnAuthorizedException, NoteNotTrashedException, NoteNotFoundException {
		Optional<Note> optionalNote = validateNoteAndUser(noteId, userId);

		if (!optionalNote.get().isTrashed()) {
			throw new NoteNotTrashedException("Note is not trashed yet");
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
			throws  ReminderDateNotValidException, UnAuthorizedException, NoteNotFoundException {
		if (Utility.validateReminder(reminder)) {
			throw new ReminderDateNotValidException("Reminder date is invalid");
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
	public NoteDTO viewNote(String noteId, String userId) throws NoteNotFoundException, UnAuthorizedException {
		Optional<Note> note = validateNoteAndUser(noteId, userId);
		if (note.get().isTrashed()) {
			throw new NoteNotFoundException(
					"Note with given id could not be found or note you are looking for might have been trashed");
		}
		return modelMapper.map(note.get(), NoteDTO.class);

	}

	@Override
	public List<NoteDTO> viewAllNotes(String userId) throws NoteNotFoundException {
		List<Note> note = noteRespository.findAllByUserIdAndIsTrashed(userId, false);
		if (note.isEmpty()) {
			throw new NoteNotFoundException("Notes are not available.kindly create note");
		}
		List<NoteDTO> noteList = new ArrayList<>();
		for (int i = 0; i < note.size(); i++) {
			noteList.add(modelMapper.map(note.get(i), NoteDTO.class));
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
	public Iterable<NoteDTO> viewAllTrashedNotes(String userId) throws NoteNotTrashedException {
		List<Note> note = noteRespository.findAllByUserIdAndIsTrashed(userId, true);
		if (note.isEmpty()) {
			throw new NoteNotTrashedException("Notes are not available in trash");
		}
		List<NoteDTO> noteList = new ArrayList<>();
		for (int i = 0; i < note.size(); i++) {
			noteList.add(modelMapper.map(note.get(i), NoteDTO.class));
		}
		return noteList;
	}

	@Override
	public Iterable<NoteDTO> getArchiveNotes(String userId) throws NoteNotFoundException {
		List<Note> note = noteRespository.findAllByUserIdAndIsArchive(userId, true);
		if (note.isEmpty()) {
			throw new NoteNotFoundException("Notes are not available");
		}
		List<NoteDTO> noteList = new ArrayList<>();
		for (int i = 0; i < note.size(); i++) {
			noteList.add(modelMapper.map(note.get(i), NoteDTO.class));
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
	public List<LabelDTO> getLabels(String userId) throws LabelNotFoundException {
		List<Label> labelList = labelRepository.findAllByUserId(userId);
		if (labelList.isEmpty()) {
			throw new LabelNotFoundException("Labels are not available");
		}
		List<LabelDTO> labelListView = new ArrayList<>();
		for (int i = 0; i < labelList.size(); i++) {
			labelListView.add(modelMapper.map(labelList.get(i), LabelDTO.class));
		}
		return labelListView;
	}

	@Override
	public LabelDTO createLabel(String labelName, String userId) throws UnAuthorizedException, LabelNotFoundException {
		if (!userRepository.findById(userId).isPresent()) {
			throw new UnAuthorizedException("User not found");
		}
		Label labelFound = labelRepository.findByLabelIdAndUserId(labelName, userId);
		if (labelFound != null) {
			return modelMapper.map(labelFound, LabelDTO.class);
		}
		Label label = new Label();
		label.setLabelName(labelName);
		label.setUserId(userId);
		labelRepository.save(label);
		return modelMapper.map(label, LabelDTO.class);

	}

	@Override
	public void deleteLabel(String labelId, String userId) throws LabelNotFoundException, UserNotFoundException {
		if (!userRepository.findById(userId).isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		Label labelFound = labelRepository.findByLabelIdAndUserId(labelId, userId);
		if (labelFound == null) {
			throw new LabelNotFoundException("Label not found");
		}
		labelRepository.deleteByLabelId(labelId);
		List<Note> noteList = noteRespository.findAllByQuery(userId, labelId);

		for (int i = 0; i < noteList.size(); i++) {

			for (int j = 0; j < noteList.get(i).getLabels().size(); j++) {

				if (noteList.get(i).getLabels().get(j).getLabelId().equals(labelId)) {
					noteList.get(i).getLabels().remove(j);
					Note note = noteList.get(i);
					noteRespository.save(note);
					break;
				}

			}

		}
	}

	@Override
	public void renameLabel(String labelId, String userId, String newLabelName)
			throws LabelNotFoundException, UserNotFoundException {
		if (!userRepository.findById(userId).isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		Label labelFound = labelRepository.findByLabelIdAndUserId(labelId, userId); // optional return
		if (labelFound == null) {
			throw new LabelNotFoundException("Label not found");
		}
		labelFound.setLabelName(newLabelName);
		labelRepository.save(labelFound);

		List<Note> noteList = noteRespository.findAllByQuery(userId, labelId);

		for (int i = 0; i < noteList.size(); i++) {

			for (int j = 0; j < noteList.get(i).getLabels().size(); j++) {

				if (noteList.get(i).getLabels().get(j).getLabelId().equals(labelId)) {
					noteList.get(i).getLabels().get(j).setLabelName(newLabelName);
					Note note = noteList.get(i);
					noteRespository.save(note);
					break;
				}

			}
		}

	}

	@Override
	public Iterable<NoteDTO> getNotesOfLabel(String labelId, String userId)
			throws  LabelNotFoundException, UserNotFoundException {
		if (!userRepository.findById(userId).isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		Label labelFound = labelRepository.findByLabelIdAndUserId(labelId, userId);
		if (labelFound == null) {
			throw new LabelNotFoundException("Label not found");
		}

		List<Note> noteList = noteRespository.findAllByQuery(userId, labelId);
		List<NoteDTO> listOfNote = new ArrayList<>();
		for (int i = 0; i < noteList.size(); i++) {
			listOfNote.add(modelMapper.map(noteList.get(i), NoteDTO.class));
		}
		return listOfNote;

	}

	@Override
	public void addLabel(String labelId, String userId, String noteId)
			throws  LabelNameAlreadyUsedException, UnAuthorizedException, NoteNotFoundException {
		Optional<Note> note = validateNoteAndUser(noteId, userId);

		// Check if note has a list of labels or not, if not ,then create a new List
		if (note.get().getLabels() == null) {
			List<LabelDTO> newLabelList = new ArrayList<>();
			note.get().setLabels(newLabelList);
		}

		// check if label is present in labelRepository
		Label labelFound = labelRepository.findByLabelIdAndUserId(labelId, userId);
		LabelDTO label = new LabelDTO();

		// if label present in the note matches with the given label,throw exception as
		// a note cannot have multiple labels with same name
		for (int i = 0; i < note.get().getLabels().size(); i++) {
			if (note.get().getLabels().get(i).getLabelId().equals(labelId)) {
				throw new LabelNameAlreadyUsedException("Label already present");
			}
		}

		label.setLabelId(labelFound.getLabelId());
		label.setLabelName(labelFound.getLabelName());
		note.get().getLabels().add(label);
		noteRespository.save(note.get());

	}
	
	
	private NoteDTO mapModels(Note note) {
		NoteDTO noteDTO=new NoteDTO();
		noteDTO.setTitle(note.getTitle());
		noteDTO.setDescription(note.getDescription());
		noteDTO.setColor(note.getColor());
		noteDTO.setCreatedAt(note.getCreatedAt());
		noteDTO.setArchive(note.isArchive());
		noteDTO.setReminder(note.getReminder());
		noteDTO.setNoteId(note.getNoteId());
		noteDTO.setPin(note.isPin());
		noteDTO.setUpdatedAt(note.getUpdatedAt());
		noteDTO.setLabels(note.getLabels());
		return noteDTO;
	}


}
