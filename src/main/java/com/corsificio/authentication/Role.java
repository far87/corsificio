package com.corsificio.authentication;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="role")
public class Role implements GrantedAuthority{

	@Id
	private Long id;
	private String role;
	
	public Role() {};

	public Role(Long id, String role) {
		super();
		this.id = id;
		this.role = role;
	}

	public void setRole(String role) {
		this.role=role;
	}
	
	public String getRole() {
		return this.role;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return role;
	}
	
	
}
