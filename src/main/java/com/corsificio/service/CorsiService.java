
package com.corsificio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corsificio.model.Corso;
import com.corsificio.repository.CorsiRepo;

@Service
public class CorsiService {
	
	@Autowired
	CorsiRepo corsiRepo;

	public CorsiService(CorsiRepo corsiRepo) {
		this.corsiRepo=corsiRepo;
	}

	public List<Corso> findAll() {
		return	corsiRepo.findAll();
	
	}

	public Optional<Corso> findByDescrizione(String descrizione) {
		return corsiRepo.findByDescrizione(descrizione);
	}

}

