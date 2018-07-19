package com.bridgelabz.fundoonoteapp.note.services;

import java.util.Date;
import java.util.List;
import com.bridgelabz.fundoonoteapp.note.exceptions.NoteException;
import com.bridgelabz.fundoonoteapp.note.models.CreateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.UpdateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.ViewNoteDTO;

public interface NoteService {

void createNote(CreateNoteDTO createNoteDTO, String token) throws NoteException;

void emptyTrash() throws NoteException;
void updateNote(UpdateNoteDTO updateNoteDTO, String token, String noteID) throws NoteException;

void deletetrashedNote(String noteId) throws NoteException;

void trashNote(String token, String noteId) throws NoteException;

void addReminder(String noteId, String token, Date reminder) throws NoteException;

void removeRemiander(String noteId, String token) throws NoteException;

ViewNoteDTO viewNote(String noteId) throws NoteException;

List<ViewNoteDTO> viewAllNotes(String token);

}
