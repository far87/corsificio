package com.corsificio;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.corsificio.authentication.User;
import com.corsificio.authentication.UserController;
import com.corsificio.repository.CorsiRepo;
import com.corsificio.repository.UserRepository;
import com.corsificio.web.CorsiController;
import com.corsificio.web.RedirectController;
import com.corsificio.webrest.CorsiControllerRest;

@WebMvcTest(controllers = {RedirectController.class,CorsiController.class,CorsiControllerRest.class,UserController.class})
public class SecurityTest {

	@MockBean
	CorsiRepo corsiRepo;
	
	@MockBean
	RabbitTemplate rabbitTemplate;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	PasswordEncoder passwordEncoder;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	void permitLogin() throws Exception{
		mockMvc.perform(get("/login"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void unhauthorizedSaveUserTest() throws Exception{
		mockMvc.perform(post("/admin/save")).andExpect(status().isForbidden());
	}


	@Test
	public void unauthorizedAccessTest() throws Exception{
		mockMvc.perform(get("/")).andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser
	public void authorizedAccessTest() throws Exception{
		mockMvc.perform(get("/")).andExpect(status().is3xxRedirection());
	}

	

	
}
