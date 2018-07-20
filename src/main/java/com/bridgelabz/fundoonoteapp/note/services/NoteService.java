package com.bridgelabz.fundoonoteapp.note.services;

import java.util.Date;
import java.util.List;
import com.bridgelabz.fundoonoteapp.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonoteapp.note.exceptions.UnAuthorizedException;
import com.bridgelabz.fundoonoteapp.note.models.CreateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.Note;
import com.bridgelabz.fundoonoteapp.note.models.UpdateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.ViewNoteDTO;

public interface NoteService {

	Note createNote(CreateNoteDTO createNoteDTO, String userId) throws NoteNotFoundException, UnAuthorizedException;

	void emptyTrash(String token) throws NoteNotFoundException, UnAuthorizedException;

	void updateNote(UpdateNoteDTO updateNoteDTO, String token) throws NoteNotFoundException, UnAuthorizedException;

	void deletetrashedNote(String noteId, String token) throws NoteNotFoundException, UnAuthorizedException;

	void trashNote(String token, String noteId) throws NoteNotFoundException, UnAuthorizedException;

	void addReminder(String noteId, String token, Date reminder) throws NoteNotFoundException, UnAuthorizedException;

	void removeRemiander(String noteId, String token) throws NoteNotFoundException, UnAuthorizedException;

	ViewNoteDTO viewNote(String noteId, String token) throws NoteNotFoundException, UnAuthorizedException;

	List<ViewNoteDTO> viewAllNotes(String token) throws NoteNotFoundException;


	/* void validateNoteAndUser(String noteId, String userId) throws UnAuthorizedException, NoteNotFoundException;*/

}
