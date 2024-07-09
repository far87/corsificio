package com.corsificio.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corsificio.model.Corso;
import com.corsificio.repository.CorsiRepo;

@Controller
@RequestMapping("/corsi")
public class CorsiController {

	@Autowired
	CorsiRepo corsiRepo;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@GetMapping
	public String corsi(Model model) {
		List<Corso> listaCorsi=corsiRepo.findAll();
		List<String> corsi=new ArrayList<>();
		for(Corso a : listaCorsi) {
			corsi.add(a.getDescrizione());
		}
		model.addAttribute("corsi",corsi);
		return "corso";
	}
/* Da rivedere logica*/
	@PostMapping
	public ResponseEntity<?> post(String corso){
		Optional<Corso> scelto=corsiRepo.findByDescrizione(corso);
		Corso corsoScelto=scelto.get();
		if(corsoScelto.getDisponibile()) {
			rabbitTemplate.convertAndSend("corso_scelto","corso.sceltoDautente", corsoScelto.getId());
			return new ResponseEntity<>("Il corso è disponibile e dura "+corsoScelto.getOre()+" ore.",HttpStatus.OK);
		}
		return new ResponseEntity<>("Il corso non è disponibile ",HttpStatus.OK);
	}
}
