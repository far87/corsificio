package com.corsificio.webrest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.corsificio.model.Corso;
import com.corsificio.model.PropertyClass;
import com.corsificio.repository.CorsiRepo;

@RestController
@RequestMapping(path="/rest", produces="application/json")
@CrossOrigin(origins="http//:corsificio:8080")
public class CorsiControllerRest {
	
	@Autowired
	CorsiRepo corsiRepo;
	
	@Autowired
	PropertyClass prop;
	
	@GetMapping(path = "/corsi")
	public List<Corso> getCorsi(){
		List<Corso> corsi=corsiRepo.findAll();
		return corsi;
	}
	
	/**
	 * Returns the course with the specified id as request param.
	 * If the resource is not present it returns null and status code 200
	 * @param id
	 * @return single course
	 */
	
	@GetMapping(path = "/corsi", params = "id")
	public Optional<Corso> getCorsiById(String id){
		Long idCorso=Long.parseLong(id);
		Optional<Corso> corso=corsiRepo.findById(idCorso);
		return corso;
	}
	
	/**
	 * Returns the course with the specified id as path variable.
	 * If the resource is not present it returns status code 404
	 * @param id
	 * @return single course
	 */
	
	@GetMapping(path = "/corsi/{id}")
	public ResponseEntity<Corso> getCorsiById(@PathVariable(name = "id") Long id){
		Optional<Corso> corso=corsiRepo.findById(id);
		if(corso.isPresent())
			return new ResponseEntity<>(corso.get(),HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	@PostMapping(path = "/corso", consumes = "application/json")
	public ResponseEntity<?> saveCorso(@RequestBody Corso corso) {
		if(corso.getId()!= null)return new ResponseEntity<>("The id field must be null",HttpStatus.BAD_REQUEST);
		Corso corsoSalvato= corsiRepo.save(corso);
		return new ResponseEntity<>(corsoSalvato, HttpStatus.CREATED);
	}
	
	/**
	 * Esempio di utilizzo di RestTemplate che chiama delle api di Google
	 * @param query
	 * @return json 
	 */
	@GetMapping(path = "/books")
	public String getBooks(@RequestParam String query) {
		RestTemplate rest= new RestTemplate();
		return rest.getForObject("https://www.googleapis.com/books/v1/volumes?q={query}", String.class, query);
	}
	
	@GetMapping("/prop")
	public PropertyClass getprop(){
		return prop;
	}
	

}
