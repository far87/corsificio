package com.corsificio.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.corsificio.authentication.User;
import com.corsificio.repository.UserRepository;

@Configuration
public class SecurityConfig  {
	
	@Autowired
	UserRepository userRepo;
	
	/**
	 * it must be used both in save user phase and login phase, pwd must be not visible also in db 
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Defines autorization rules
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests().requestMatchers("/login").permitAll().
		requestMatchers("/admin","/admin/**").hasRole("ADMIN").
		requestMatchers("/","/corsi").authenticated().
		anyRequest().permitAll();
		http.formLogin();
		http.csrf().disable();
		return http.build();
		
	}
	
	/**
	 * Manages customized authentication
	 * @return authenticated user
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		return (username)->{
			User user= userRepo.findByUsername(username);
			return user;
		};
	}

	
}
