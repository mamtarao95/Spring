package com.bridgelabz.fundoonoteapp.note.controllers;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonoteapp.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonoteapp.note.exceptions.UnAuthorizedException;
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
	public ResponseEntity<ViewNoteDTO> createNote(@RequestBody CreateNoteDTO createNoteDTO,
			@RequestAttribute("userId") String userId, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException {
		ViewNoteDTO note = noteService.createNote(createNoteDTO, userId);
		return new ResponseEntity<>(note, HttpStatus.CREATED);
	}

	@PutMapping("/deletenote/{noteId}")
	public ResponseEntity<Response> deleteNote(HttpServletRequest request, @RequestAttribute("userId") String userId,
			@PathVariable String noteId) throws NoteNotFoundException, UnAuthorizedException {
		noteService.trashNote(userId, noteId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Note trashed Successfully!!");
		responseDTO.setStatus(2);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/updatenote/{noteId}")
	public ResponseEntity<Response> updateNote(@RequestBody UpdateNoteDTO updateNoteDTO,
			@RequestAttribute("userId") String userId, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException {
		noteService.updateNote(updateNoteDTO, userId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Updated Note Successfull!!");
		responseDTO.setStatus(3);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@GetMapping("/getnote/{noteId}")
	public ResponseEntity<ViewNoteDTO> viewNote(@PathVariable String noteId, @RequestAttribute("userId") String userId,
			HttpServletRequest request) throws NoteNotFoundException, UnAuthorizedException {
		return new ResponseEntity<>(noteService.viewNote(noteId, userId), HttpStatus.OK);
	}

	@DeleteMapping("/deleteForeverOrRestoreNote/{noteId}")
	public ResponseEntity<Response> deleteOrRestoreTrashedNote(@PathVariable String noteId,
			@RequestAttribute("userId") String userId, HttpServletRequest request,@RequestBody boolean isdelete)
			throws NoteNotFoundException, UnAuthorizedException {
		noteService.deleteOrRestoreTrashedNote(noteId, userId,isdelete);
		Response responseDTO = new Response();
		responseDTO.setMessage("Deleted trashed Note Successfully!!");
		responseDTO.setStatus(4);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/emptyTrash")
	public ResponseEntity<Response> emptyTrash(@RequestAttribute("userId") String userId, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException {
		noteService.emptyTrash(userId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Trash is emptied Successfully!!");
		responseDTO.setStatus(5);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@PostMapping("/removereminder/{noteId}")
	public ResponseEntity<Response> removeRemainder(@PathVariable String noteId,
			@RequestAttribute("userId") String userId, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException {
		noteService.removeRemiander(noteId, userId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Reminder removed Successfully!!");
		responseDTO.setStatus(6);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@PutMapping("/addreminder/{noteId}")
	public ResponseEntity<Response> addReminder(@PathVariable String noteId, HttpServletRequest request,
			@RequestBody Date reminder) throws NoteNotFoundException, UnAuthorizedException {
		noteService.addReminder(noteId, request.getAttribute("userId").toString(), reminder);
		Response responseDTO = new Response();
		responseDTO.setMessage("Reminder added Successfully!!");
		responseDTO.setStatus(7);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@GetMapping("/getallnotes")
	public Iterable<ViewNoteDTO> viewAllNotes(HttpServletRequest request, @RequestAttribute("userId") String userId)
			throws NoteNotFoundException {
		return noteService.viewAllNotes(userId);
	}

	
	@GetMapping("/getalltrashednotes")
	public Iterable<ViewNoteDTO> viewAllTrashedNotes(HttpServletRequest request, @RequestAttribute("userId") String userId) throws NoteNotFoundException{
		return noteService.viewAllTrashedNotes(userId);
	}
	
	@GetMapping("/getarchivenotes")
	public Iterable<ViewNoteDTO> geArchiveNotes(HttpServletRequest request, @RequestAttribute("userId") String userId) throws NoteNotFoundException{
		return noteService.geArchiveNotes(userId);
	}
	
	

}
