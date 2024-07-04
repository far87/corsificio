package com.corsificio.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.corsificio.repository.UserRepository;

@RestController
@RequestMapping("/admin")
public class UserController {

	@Autowired
	UserRepository userRepo;
	
	@PostMapping("/save")
	public User saveUser(@RequestBody User user){
		return userRepo.save(user);
	}
}
