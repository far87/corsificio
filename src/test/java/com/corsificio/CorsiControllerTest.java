package com.corsificio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.corsificio.model.Corso;
import com.corsificio.repository.CorsiRepo;
import com.corsificio.web.CorsiController;

@WebMvcTest(controllers = CorsiController.class)
public class CorsiControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean 
	CorsiRepo corsiRepo;
	
	@MockBean
	RabbitTemplate rabbitTemplate;
	
	@Test
	@WithMockUser
	void corsiTest() throws Exception{
		String html=
				mockMvc.perform(get("/corsi"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		verify(corsiRepo).findAll();
		assertThat(html).contains("<form action=\"/corsi\" method=\"POST\">");
		
	}
	
	@Test
	@WithMockUser
	void postCorsoDisponibileTest() throws Exception{
		
		//imposto un corso disponibile da farmi ritornare
		Corso corso=new Corso();
		corso.setDescrizione("FORMAZIONE GENERALE");
		corso.setDisponibile(true);
		corso.setOre(8);
		
		//imposto il mock in modo da farmi ritornare il corso impostato
		when(corsiRepo.findByDescrizione(anyString())).thenReturn(Optional.of(corso));
		
		//effettuo chiamata e verifico corretteza della response
		mockMvc.perform(post("/corsi")
				.param("corso","FORMAZIONE GENERALE").with(csrf()))
		.andExpect(status().isOk()).andExpect(content().string(containsString("Il corso è disponibile")));
		
		//verifico che il metodo sul rabbitTemplate sia stato correttamente chiamato
		verify(rabbitTemplate).convertAndSend("corso_scelto","corso.sceltoDautente", corso.getId());
	}
	
	@Test
	@WithMockUser
	void postCorsoNonDisponibileTest() throws Exception{
		
		//imposto un corso con disponibiltà false
		Corso corso=new Corso();
		corso.setDescrizione("FORMAZIONE GENERALE");
		corso.setDisponibile(false);
		corso.setOre(8);
		
		//imposto il mock in modo da farmi ritornare il corso impostato
		when(corsiRepo.findByDescrizione(anyString())).thenReturn(Optional.of(corso));
		
		//effettuo chiamata e verifico corretteza della response
		mockMvc.perform(post("/corsi")
				.param("corso","FORMAZIONE GENERALE").with(csrf()))
		.andExpect(status().isOk()).andExpect(content().string(containsString("Il corso non è disponibile")));
		
	}


}
