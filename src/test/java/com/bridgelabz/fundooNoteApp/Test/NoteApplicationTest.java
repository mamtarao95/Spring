/*package com.bridgelabz.fundooNoteApp.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import com.bridgelabz.fundoonoteapp.FundooNoteApplication;
import com.bridgelabz.fundoonoteapp.note.controllers.NoteController;
import com.bridgelabz.fundoonoteapp.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonoteapp.note.exceptions.UnAuthorizedException;
import com.bridgelabz.fundoonoteapp.note.models.CreateNoteDTO;
import com.bridgelabz.fundoonoteapp.note.models.Note;
import com.bridgelabz.fundoonoteapp.note.models.NoteViewDTO;
import com.bridgelabz.fundoonoteapp.note.repositories.NoteRespository;
import com.bridgelabz.fundoonoteapp.note.services.NoteServiceImpl;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = FundooNoteApplication.class)
@SpringBootTest
public class NoteApplicationTest {

	private MockMvc mockMvc;
	
	@Autowired
	ModelMapper  modelMappper;
	@Autowired
	private NoteController noteController;

	@InjectMocks
	private NoteServiceImpl noteService;
	
	@Mock
	private NoteRespository noteRespository;
	
	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
	}
	
	
	//@Test
		public void createNoteTest() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/createnote").contentType(MediaType.APPLICATION_JSON)
					.content("{\"description\" : \"notedescription\", \"title\" : \"title\",\"color\" : \"white\",\"reminder\" : \"2018-07-21T10:02:43.210Z\",\"isPin\" : \"false\",\"isArchive\" : \"false\"}")
					.requestAttr("userId", "5b5014124b47f81fbe60ce52")
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.*", hasSize(7)))
			.andDo(print());
		}
	
	//@Test
		public void deleteNoteTest() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.put("/fundoo/deletenote/{noteId}","5b558dec4b47f8117152b246")
					.requestAttr("userId","5b5014124b47f81fbe60ce52")
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.message").value("Note trashed Successfully!!"))
					.andExpect(jsonPath("$.status").value(2))
					.andDo(print());
		}
	
	
	//@Test
	public void updateNoteTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/fundoo/updatenote/{noteId}","5b558dec4b47f8117152b246").contentType(MediaType.APPLICATION_JSON)
				.content("{\"description\" : \"notedescription\", \"title\" : \"title\",\"noteId\" : \"5b558dec4b47f8117152b246\"}")
				.requestAttr("userId","5b5014124b47f81fbe60ce52")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Updated Note Successfull!!"))
				.andExpect(jsonPath("$.status").value(3))
				.andDo(print());
	}
	
	
	//@Test
	public void getNoteTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/fundoo/getnote/{noteId}","5b5594014b47f81b502c09b4").contentType(MediaType.APPLICATION_JSON)
				.requestAttr("userId","5b5014124b47f81fbe60ce52")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.*", hasSize(7)))
			.andDo(print());
	}
	

	//@Test
	public void deleteOrRestoreTrashedNoteTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/fundoo/deleteForeverOrRestoreNote/{noteId}","5b5594014b47f81b502c09b4").contentType(MediaType.APPLICATION_JSON)
				.requestAttr("userId","5b5014124b47f81fbe60ce52")
				.content("true")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.message").value("Deleted trashed Note Successfully!!"))
		.andExpect(jsonPath("$.status").value(4))
		.andDo(print());
	}
	
	//@Test
	public void testGetNote() throws UnAuthorizedException, NoteNotFoundException{
		 NoteViewDTO note=new NoteViewDTO("5b5594014b47f81b502c09b4", "title", "notedescription", new java.util.Date(System.currentTimeMillis()),  new java.util.Date(System.currentTimeMillis()),  new java.util.Date(System.currentTimeMillis()),
					"white");
		String userId="5b5014124b47f81fbe60ce52";
		CreateNoteDTO createNoteDTO=new CreateNoteDTO("title","description","color",new java.util.Date(System.currentTimeMillis()),false,false);
		Mockito.when(noteService.createNote(createNoteDTO,userId)).thenReturn(note);
		assertArrayEquals(expecteds, actuals);
	assertEquals(message, expected, actual);
	}
}


*/