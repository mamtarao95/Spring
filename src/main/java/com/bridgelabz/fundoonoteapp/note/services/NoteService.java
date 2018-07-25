package com.bridgelabz.fundoonoteapp.note.services;

import java.util.Date;
import java.util.List;

import com.bridgelabz.fundoonoteapp.note.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonoteapp.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonoteapp.note.exceptions.UnAuthorizedException;
import com.bridgelabz.fundoonoteapp.note.models.CreateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.LabelDTO;
import com.bridgelabz.fundoonoteapp.note.models.UpdateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.NoteDTO;

public interface NoteService {

	NoteDTO createNote(CreateNoteDTO createNoteDTO, String userId)
			throws NoteNotFoundException, UnAuthorizedException, LabelNotFoundException;

	void emptyTrash(String token) throws NoteNotFoundException, UnAuthorizedException;

	void updateNote(UpdateNoteDTO updateNoteDTO, String token) throws NoteNotFoundException, UnAuthorizedException;

	void deleteOrRestoreTrashedNote(String noteId, String token, boolean input)
			throws NoteNotFoundException, UnAuthorizedException;

	void trashNote(String token, String noteId) throws NoteNotFoundException, UnAuthorizedException;

	void addReminder(String noteId, String token, Date reminder) throws NoteNotFoundException, UnAuthorizedException;

	void removeRemiander(String noteId, String token) throws NoteNotFoundException, UnAuthorizedException;

	NoteDTO viewNote(String noteId, String token) throws NoteNotFoundException, UnAuthorizedException;

	List<NoteDTO> viewAllNotes(String token) throws NoteNotFoundException;

	Iterable<NoteDTO> viewAllTrashedNotes(String userId) throws NoteNotFoundException;

	Iterable<NoteDTO> getArchiveNotes(String userId) throws NoteNotFoundException;

	void setArchive(String userId, String noteId) throws NoteNotFoundException, UnAuthorizedException;

	void unArchive(String userId, String noteId) throws UnAuthorizedException, NoteNotFoundException;

	void pinNote(String userId, String noteId) throws UnAuthorizedException, NoteNotFoundException;

	void unPinNote(String userId, String noteId) throws UnAuthorizedException, NoteNotFoundException;

	List<LabelDTO> getLabels(String userId) throws LabelNotFoundException;

	LabelDTO createLabel(String labelName, String userId) throws UnAuthorizedException, LabelNotFoundException;

	void deleteLabel(String labelName, String userId) throws UnAuthorizedException, LabelNotFoundException;

	Iterable<LabelDTO> getNotesOfLabel(String labelName, String userId) throws UnAuthorizedException, LabelNotFoundException;

	void addLabel(String labelName, String userId, String noteId) throws UnAuthorizedException, NoteNotFoundException, LabelNotFoundException;

	void renameLabel(String labelId, String userId, String newLabelName) throws UnAuthorizedException, LabelNotFoundException;

}
