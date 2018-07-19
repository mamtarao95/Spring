package com.bridgelabz.fundoonoteapp.note.controllers;

import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonoteapp.note.exceptions.NoteException;
import com.bridgelabz.fundoonoteapp.note.models.CreateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.UpdateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.ViewNoteDTO;
import com.bridgelabz.fundoonoteapp.note.services.NoteService;
import com.bridgelabz.fundoonoteapp.user.models.Response;

@RestController
@RequestMapping("/api/note")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@PostMapping("/createnote")
	public ResponseEntity<Response> createNote(@RequestBody CreateNoteDTO createNoteDTO, @RequestParam("token") String token) throws NoteException {
		noteService.createNote(createNoteDTO, token);
		Response responseDTO = new Response();
		responseDTO.setMessage("Note created Successfully!!");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

	}

	@DeleteMapping("/deletenote")
	public ResponseEntity<Response> deleteNote(@RequestParam String token,@RequestParam String noteId) throws NoteException {
		noteService.trashNote(token, noteId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Note trashed Successfully!!");
		responseDTO.setStatus(2);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PostMapping("/updatenote")
	public ResponseEntity<Response> updateNote(@RequestBody UpdateNoteDTO updateNoteDTO, @RequestParam String token,
			String noteId) throws NoteException {
		noteService.updateNote(updateNoteDTO, token, noteId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Updated Note Successfull!!");
		responseDTO.setStatus(3);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@GetMapping("/viewnote")
	public ResponseEntity<ViewNoteDTO> viewNote(@RequestParam String noteId) throws NoteException {
		return new ResponseEntity<>(noteService.viewNote(noteId), HttpStatus.OK);
		}

	@DeleteMapping("/deleteTrashedNote")
	public ResponseEntity<Response> deleteTrashedNote(@RequestParam String noteId)
			throws NoteException {
		noteService.deletetrashedNote(noteId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Deleted trashed Note Successfully!!");
		responseDTO.setStatus(4);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@DeleteMapping("/emptyTrash")
	public ResponseEntity<Response> emptyTrash(HttpServletResponse response) throws NoteException {
		noteService.emptyTrash();
		Response responseDTO = new Response();
		responseDTO.setMessage("Trash is emptied Successfully!!");
		responseDTO.setStatus(5);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@PostMapping("/removereminder")
	public ResponseEntity<Response> removeRemainder(@RequestParam String noteId, String token) throws NoteException {
		noteService.removeRemiander(noteId, token);
		Response responseDTO = new Response();
		responseDTO.setMessage("Reminder removed Successfully!!");
		responseDTO.setStatus(6);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@PostMapping("/addreminder")
	public ResponseEntity<Response> addReminder(@RequestParam String noteId, String token, @RequestBody Date reminder)
			throws NoteException {
		noteService.addReminder(noteId, token, reminder);
		Response responseDTO = new Response();
		responseDTO.setMessage("Reminder added Successfully!!");
		responseDTO.setStatus(7);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}
	
	@GetMapping("/viewAll")
	public Iterable<ViewNoteDTO> viewAllNotes(@RequestParam String token)
			throws NoteException {
		return noteService.viewAllNotes(token);
	
	}

}
