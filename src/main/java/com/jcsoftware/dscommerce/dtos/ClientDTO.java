package com.jcsoftware.dscommerce.dtos;

import com.jcsoftware.dscommerce.entities.User;

public class ClientDTO {

	private Long id;
	private String name;
	
	public ClientDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public ClientDTO(User entity) {
		super();
		this.id = entity.getId();
		this.name = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	
	
}
