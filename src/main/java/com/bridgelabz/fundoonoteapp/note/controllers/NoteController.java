package com.bridgelabz.fundoonoteapp.note.controllers;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonoteapp.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonoteapp.note.exceptions.UnAuthorizedException;
import com.bridgelabz.fundoonoteapp.note.models.CreateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.Note;
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
	public ResponseEntity<Note> createNote(@RequestBody CreateNoteDTO createNoteDTO,HttpServletRequest request,HttpServletResponse response,@RequestParam String token) throws NoteNotFoundException, UnAuthorizedException {
		Note note=noteService.createNote(createNoteDTO,token);
		return new ResponseEntity<>(note, HttpStatus.CREATED);

	}

	@DeleteMapping("/deletenote/{noteId}")
	public ResponseEntity<Response> deleteNote(@RequestParam String token,@PathVariable String noteId) throws NoteNotFoundException, UnAuthorizedException {
		noteService.trashNote(token, noteId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Note trashed Successfully!!");
		responseDTO.setStatus(2);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PostMapping("/updatenote/{noteId}")
	public ResponseEntity<Response> updateNote(@RequestBody UpdateNoteDTO updateNoteDTO, @RequestParam String token) throws NoteNotFoundException, UnAuthorizedException {
		noteService.updateNote(updateNoteDTO, token);
		Response responseDTO = new Response();
		responseDTO.setMessage("Updated Note Successfull!!");
		responseDTO.setStatus(3);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@GetMapping("/viewnote/{noteId}")
	public ResponseEntity<ViewNoteDTO> viewNote(@PathVariable String noteId,@RequestParam String token) throws NoteNotFoundException, UnAuthorizedException {
		return new ResponseEntity<>(noteService.viewNote(noteId,token), HttpStatus.OK);
		}

	@DeleteMapping("/deleteTrashedNote/{noteId}")
	public ResponseEntity<Response> deleteTrashedNote(@PathVariable String noteId,@RequestParam String token)
			throws NoteNotFoundException, UnAuthorizedException {
		noteService.deletetrashedNote(noteId,token);
		Response responseDTO = new Response();
		responseDTO.setMessage("Deleted trashed Note Successfully!!");
		responseDTO.setStatus(4);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@DeleteMapping("/emptyTrash")
	public ResponseEntity<Response> emptyTrash(HttpServletResponse response,@RequestParam String token) throws NoteNotFoundException, UnAuthorizedException {
		noteService.emptyTrash(token);
		Response responseDTO = new Response();
		responseDTO.setMessage("Trash is emptied Successfully!!");
		responseDTO.setStatus(5);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@PostMapping("/removereminder/{noteId}")
	public ResponseEntity<Response> removeRemainder(@PathVariable String noteId,@RequestParam String token) throws NoteNotFoundException, UnAuthorizedException {
		noteService.removeRemiander(noteId, token);
		Response responseDTO = new Response();
		responseDTO.setMessage("Reminder removed Successfully!!");
		responseDTO.setStatus(6);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@PostMapping("/addreminder/{noteId}")
	public ResponseEntity<Response> addReminder(@PathVariable String noteId, @RequestParam String token, @RequestBody Date reminder)
			throws NoteNotFoundException, UnAuthorizedException {
		noteService.addReminder(noteId, token, reminder);
		Response responseDTO = new Response();
		responseDTO.setMessage("Reminder added Successfully!!");
		responseDTO.setStatus(7);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}
	
	@GetMapping("/viewAll")
	public Iterable<ViewNoteDTO> viewAllNotes(@RequestParam String token)
			throws NoteNotFoundException {
		return noteService.viewAllNotes(token);
	
	}

}
