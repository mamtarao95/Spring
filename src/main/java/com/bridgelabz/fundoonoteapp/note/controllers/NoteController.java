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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonoteapp.note.exceptions.LabelNameAlreadyUsedException;
import com.bridgelabz.fundoonoteapp.note.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonoteapp.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonoteapp.note.exceptions.NoteNotTrashedException;
import com.bridgelabz.fundoonoteapp.note.exceptions.ReminderDateNotValidException;
import com.bridgelabz.fundoonoteapp.note.exceptions.UnAuthorizedException;
import com.bridgelabz.fundoonoteapp.note.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonoteapp.note.models.CreateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.LabelDTO;
import com.bridgelabz.fundoonoteapp.note.models.UpdateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.NoteDTO;
import com.bridgelabz.fundoonoteapp.note.services.NoteService;
import com.bridgelabz.fundoonoteapp.user.models.Response;

@RestController
@RequestMapping("/fundoo")
public class NoteController {

	@Autowired
	private NoteService noteService;

	/**
	 * @param createNoteDTO
	 * @param userId
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws LabelNotFoundException
	 * @throws ReminderDateNotValidException
	 * @throws UserNotFoundException 
	 */
	@PostMapping("/createnote")
	public ResponseEntity<NoteDTO> createNote(@RequestBody CreateNoteDTO createNoteDTO
			 ,@RequestHeader("userId") String userId,HttpServletRequest request) throws NoteNotFoundException,
			UnAuthorizedException, LabelNotFoundException, ReminderDateNotValidException, UserNotFoundException {
		NoteDTO note = noteService.createNote(createNoteDTO, userId);
		return new ResponseEntity<>(note, HttpStatus.CREATED);
	}

	/**
	 * @param userId
	 * @param noteId
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/deletenote/{noteId}")
	public ResponseEntity<Response> deleteNote(@RequestHeader("userId") String userId,
			@PathVariable("noteId") String noteId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {
		noteService.trashNote(userId, noteId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Note trashed Successfully!!");
		responseDTO.setStatus(2);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param updateNoteDTO
	 * @param userId
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/updatenote/{noteId}")
	public ResponseEntity<Response> updateNote(@RequestBody UpdateNoteDTO updateNoteDTO,
			@RequestHeader("userId") String userId, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {
		noteService.updateNote(updateNoteDTO, userId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Updated Note Successfull!!");
		responseDTO.setStatus(3);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param noteId
	 * @param userId
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@GetMapping("/getnote/{noteId}")
	public ResponseEntity<NoteDTO> viewNote(@PathVariable String noteId, @RequestHeader("userId") String userId,
			HttpServletRequest request) throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {
		return new ResponseEntity<>(noteService.viewNote(noteId, userId), HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param userId
	 * @param request
	 * @param isdelete
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws NoteNotTrashedException
	 * @throws UserNotFoundException
	 */
	@DeleteMapping("/deleteForeverOrRestoreNote/{noteId}")
	public ResponseEntity<Response> deleteOrRestoreTrashedNote(@PathVariable String noteId,
			@RequestHeader("userId") String userId, HttpServletRequest request, @RequestBody boolean isdelete)
			throws NoteNotFoundException, UnAuthorizedException, NoteNotTrashedException, UserNotFoundException {
		noteService.deleteOrRestoreTrashedNote(noteId, userId, isdelete);
		Response responseDTO = new Response();
		responseDTO.setMessage("Deleted trashed Note Successfully!!");
		responseDTO.setStatus(4);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param userId
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 */
	@DeleteMapping("/emptyTrash")
	public ResponseEntity<Response> emptyTrash(@RequestHeader("userId") String userId, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException {
		noteService.emptyTrash(userId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Trash is emptied Successfully!!");
		responseDTO.setStatus(5);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param noteId
	 * @param userId
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@PostMapping("/removereminder/{noteId}")
	public ResponseEntity<Response> removeRemainder(@PathVariable String noteId, @RequestHeader("userId") String userId,
			HttpServletRequest request) throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {
		noteService.removeRemiander(noteId, userId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Reminder removed Successfully!!");
		responseDTO.setStatus(6);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param noteId
	 * @param request
	 * @param reminder
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws ReminderDateNotValidException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/addreminder/{noteId}")
	public ResponseEntity<Response> addReminder(@PathVariable String noteId, HttpServletRequest request,
			@RequestBody Date reminder)
			throws NoteNotFoundException, UnAuthorizedException, ReminderDateNotValidException, UserNotFoundException {
		noteService.addReminder(noteId, request.getAttribute("userId").toString(), reminder);
		Response responseDTO = new Response();
		responseDTO.setMessage("Reminder added Successfully!!");
		responseDTO.setStatus(7);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param request
	 * @param userId
	 * @return
	 * @throws NoteNotFoundException
	 */
	@GetMapping("/getallnotes")
	public Iterable<NoteDTO> viewAllNotes(HttpServletRequest request, @RequestHeader("userId") String userId)
			throws NoteNotFoundException {
		return noteService.viewAllNotes(userId);
	}

	/**
	 * @param request
	 * @param userId
	 * @return
	 * @throws NoteNotFoundException
	 * @throws NoteNotTrashedException
	 */
	@GetMapping("/getalltrashednotes")
	public Iterable<NoteDTO> viewAllTrashedNotes(HttpServletRequest request, @RequestHeader("userId") String userId)
			throws NoteNotFoundException, NoteNotTrashedException {
		return noteService.viewAllTrashedNotes(userId);
	}

	/**
	 * @param request
	 * @param userId
	 * @return
	 * @throws NoteNotFoundException
	 */
	@GetMapping("/getarchivenotes")
	public Iterable<NoteDTO> getArchiveNotes(HttpServletRequest request, @RequestHeader("userId") String userId)
			throws NoteNotFoundException {
		return noteService.getArchiveNotes(userId);
	}

	/**
	 * @param request
	 * @param noteId
	 * @param userId
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/setarchive/{noteId}")
	public ResponseEntity<Response> setArchive(HttpServletRequest request, @PathVariable String noteId,
			@RequestHeader("userId") String userId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {
		noteService.setArchive(userId, noteId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Note Archived Successfully!!");
		responseDTO.setStatus(8);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param request
	 * @param noteId
	 * @param userId
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/unarchive/{noteId}")
	public ResponseEntity<Response> unArchive(HttpServletRequest request, @PathVariable String noteId,
			@RequestHeader("userId") String userId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {
		noteService.unArchive(userId, noteId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Note UnArchived Successfully!!");
		responseDTO.setStatus(9);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param request
	 * @param noteId
	 * @param userId
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/pin/{noteId}")
	public ResponseEntity<Response> pinNote(HttpServletRequest request, @PathVariable String noteId,
			@RequestHeader("userId") String userId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {
		noteService.pinNote(userId, noteId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Note pinned Successfully!!");
		responseDTO.setStatus(8);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param request
	 * @param noteId
	 * @param userId
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/unpin/{noteId}")
	public ResponseEntity<Response> unPinNote(HttpServletRequest request, @PathVariable String noteId,
			@RequestHeader("userId") String userId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {
		noteService.unPinNote(userId, noteId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Note UnPinned Successfully!!");
		responseDTO.setStatus(9);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param request
	 * @param userId
	 * @return
	 * @throws LabelNotFoundException
	 */
	@GetMapping("/viewLabels")
	public Iterable<LabelDTO> getLabels(HttpServletRequest request, @RequestHeader("userId") String userId)
			throws LabelNotFoundException {
		return noteService.getLabels(userId);
	}

	/**
	 * @param labelName
	 * @param userId
	 * @param request
	 * @return
	 * @throws UnAuthorizedException
	 * @throws LabelNotFoundException
	 */
	@PostMapping("/createlabel")
	public ResponseEntity<LabelDTO> createLabel(@RequestParam String labelName, @RequestHeader("userId") String userId,
			HttpServletRequest request) throws UnAuthorizedException, LabelNotFoundException {
		LabelDTO labelViewDTO = noteService.createLabel(labelName, userId);
		return new ResponseEntity<>(labelViewDTO, HttpStatus.CREATED);
	}

	/**
	 * @param labelId
	 * @param userId
	 * @param request
	 * @return
	 * @throws UnAuthorizedException
	 * @throws LabelNotFoundException
	 * @throws UserNotFoundException
	 */
	@DeleteMapping("/deletelabel")
	public ResponseEntity<Response> deleteLabel(@RequestParam String labelId, @RequestHeader("userId") String userId,
			HttpServletRequest request) throws UnAuthorizedException, LabelNotFoundException, UserNotFoundException {
		noteService.deleteLabel(labelId, userId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Label deleted Successfully!!");
		responseDTO.setStatus(10);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param labelId
	 * @param noteId
	 * @param userId
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws LabelNotFoundException
	 * @throws LabelNameAlreadyUsedException
	 * @throws UserNotFoundException
	 */
	@PostMapping("/addlabel")
	public ResponseEntity<Response> addLabel(@RequestParam String labelId, @RequestParam("noteId") String noteId,
			@RequestHeader("userId") String userId, HttpServletRequest request) throws NoteNotFoundException,
			UnAuthorizedException, LabelNotFoundException, LabelNameAlreadyUsedException, UserNotFoundException {
		noteService.addLabel(labelId, userId, noteId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Label added Successfully!!");
		responseDTO.setStatus(11);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param labelId
	 * @param newLabelName
	 * @param userId
	 * @param request
	 * @return
	 * @throws UnAuthorizedException
	 * @throws LabelNotFoundException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/renamelabel")
	public ResponseEntity<Response> renameLabel(@RequestParam String labelId, @RequestParam String newLabelName,
			@RequestHeader("userId") String userId)
			throws UnAuthorizedException, LabelNotFoundException, UserNotFoundException {
		noteService.renameLabel(labelId, userId, newLabelName);
		Response responseDTO = new Response();
		responseDTO.setMessage("Label renamed Successfully!!");
		responseDTO.setStatus(12);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param labelName
	 * @param userId
	 * @param request
	 * @return
	 * @throws UnAuthorizedException
	 * @throws LabelNotFoundException
	 * @throws UserNotFoundException
	 */
	@GetMapping("/getnotesoflabel")
	public Iterable<NoteDTO> getNotesOfLabel(@RequestBody String labelName, @RequestHeader("userId") String userId,
			HttpServletRequest request) throws UnAuthorizedException, LabelNotFoundException, UserNotFoundException {
		return noteService.getNotesOfLabel(labelName, userId);

	}
	
	public ResponseEntity<Response> removeLabel(@RequestParam String labelId,@RequestParam String noteId,@RequestHeader("userId") String userId) throws UserNotFoundException, LabelNotFoundException, NoteNotFoundException, UnAuthorizedException{
		noteService.removeLabel(userId,labelId,noteId);
		Response responseDTO = new Response();
		responseDTO.setMessage("Label removed Successfully!!");
		responseDTO.setStatus(13);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}

}
