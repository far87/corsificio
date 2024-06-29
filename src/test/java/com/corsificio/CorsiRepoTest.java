
package com.corsificio;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.corsificio.model.Corso;
import com.corsificio.repository.CorsiRepo;

@SpringBootTest
public class CorsiRepoTest {

	@Autowired
	CorsiRepo corsiRepo;
	
	@Test
	public void findByIdTest() {
		Optional<Corso> corso=corsiRepo.findById(2l);
		assertThat(corso).isNotEmpty();
		assertThat(corso.get().getDescrizione()).contains("SPECIFICA");
	}
	

}
