package com.corsificio;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.corsificio.web.RedirectController;

@WebMvcTest(controllers = RedirectController.class)
public class RedirectControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	/**
	 * it checks that this http request receives a redirect 302 status code
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	void redirectToCorsiTest() throws Exception{
		mockMvc.perform(get("/")).andExpect(status().is3xxRedirection());
	}
}
