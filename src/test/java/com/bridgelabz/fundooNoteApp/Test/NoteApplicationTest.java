/*package com.bridgelabz.fundooNoteApp.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bridgelabz.fundoonoteapp.FundooNoteApplication;
import com.bridgelabz.fundoonoteapp.note.controllers.NoteController;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = FundooNoteApplication.class)
@SpringBootTest
public class NoteApplicationTest {

	private MockMvc mockMvc;
	
	@Autowired
	private NoteController noteController;

	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
	}
	
	
	//@Test
		public void createNoteTest() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.post("/api/note/createnote").contentType(MediaType.APPLICATION_JSON)
					.content("{\"description\" : \"notedescription\", \"title\" : \"title\" }")
					.param("token","eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1YjUwMTQxMjRiNDdmODFmYmU2MGNlNTIiLCJpYXQiOjE1MzIwMDE5NjEsImV4cCI6MTUzMjA4ODM2MSwic3ViIjoidXNlcnRva2VuIn0.2NJA0ZS1d8OtRSe-9udNqBIHfKOmOS3mXPJBrNjo5bU")
					
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.message").value("Note created Successfully!!"))
					.andExpect(jsonPath("$.status").value(1));
		}
}
*/