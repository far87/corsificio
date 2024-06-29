package com.corsificio.web;

import java.util.ArrayList;
import java.util.List;

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
	
	@PostMapping
	public ResponseEntity<?> post(String corso){
		String base="Hai scelto ";
		return new ResponseEntity<>(base+corso,HttpStatus.OK);
	}
}
