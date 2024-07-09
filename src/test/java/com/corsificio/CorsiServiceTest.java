
package com.corsificio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.corsificio.model.Corso;
import com.corsificio.repository.CorsiRepo;
import com.corsificio.service.CorsiService;

@ExtendWith(MockitoExtension.class)
public class CorsiServiceTest {
	
	@Mock
	CorsiRepo corsiRepo;
	
	@Test
	public void findAllTest() {
		
		Corso corso=new Corso();
		corso.setDescrizione("FORMAZIONE GENERALE");
		corso.setDisponibile(true);
		corso.setOre(8);

		CorsiService corsiService=new CorsiService(corsiRepo);
		
		when(corsiRepo.findAll()).thenReturn(List.of(corso));
		
		List<Corso> corsi=corsiService.findAll();
		
		assertThat(corsi).isNotEmpty();
		assertThat(corsi.get(0).getDisponibile()).isEqualTo(true);
		verify(corsiRepo).findAll();
		
	}
	
	@Test
	public void findByDescrizioneTest() {
		Corso corso=new Corso();
		corso.setDescrizione("FORMAZIONE GENERALE");
		corso.setDisponibile(true);
		corso.setOre(8);

		CorsiService corsiService=new CorsiService(corsiRepo);
		when(corsiRepo.findByDescrizione(anyString())).thenReturn(Optional.of(corso));
		Optional<Corso> corsoFounded=corsiService.findByDescrizione("FORMAZIONE GENERALE");
		Corso corso_=corsoFounded.get();				
		assertThat(corso_.getDescrizione()).isEqualTo("FORMAZIONE GENERALE");
	}
	
	
}

