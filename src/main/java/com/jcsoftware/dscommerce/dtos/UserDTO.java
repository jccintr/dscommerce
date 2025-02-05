package com.jcsoftware.dscommerce.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.jcsoftware.dscommerce.entities.User;

public class UserDTO {
	
	private Long id;
	private String name;
	private String email;
	private String phone;
	private LocalDate birthDate;
	
	private List<String> roles = new ArrayList<>();

	public UserDTO(User entity) {
		super();
		this.id = entity.getId();
		this.name =entity.getName();
		this.email = entity.getEmail();
		this.phone = entity.getPhone();
		this.birthDate = entity.getBirthDate();
		this.roles = entity.getRoles();
		/*
		for(GrantedAuthory role: entity.getRoles()) {
			roles.add(role.getAuthority());
		}
		*/
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public List<String> getRoles() {
		return roles;
	}
	
	
	
	
	
	
}
