package com.bridgelabz.fundooNoteApp;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
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
import org.springframework.web.context.WebApplicationContext;

import com.bridgelabz.fundoonoteapp.FundooNoteApplication;
import com.bridgelabz.fundoonoteapp.user.controllers.UserController;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = FundooNoteApplication.class)
@SpringBootTest
public class FundooNoteApplicationTests {

	private MockMvc mockMvc;
	@Autowired
	private UserController userController;

	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}



	/*@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}*/

	@Test
	public void verifyLogin() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/user/login").contentType(MediaType.APPLICATION_JSON)
				.content("{\"email\" : \"mamtarao9395@gmail.com\", \"password\" : \"Mamta@111\" }")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.email").exists())
				.andExpect(jsonPath("$.password").exists())
				.andExpect(jsonPath("$.email",is("mamtarao9395@gmail.com")))
				.andExpect(jsonPath("$.password",is("Mamta@111")))
				 .andExpect(status().isConflict())
				.andDo(print());
	}
}
