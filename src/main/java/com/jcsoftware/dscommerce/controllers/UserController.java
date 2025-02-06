package com.jcsoftware.dscommerce.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcsoftware.dscommerce.dtos.UserDTO;
import com.jcsoftware.dscommerce.services.UserService;



@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	private UserService service;

	
	@PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
	//@PreAuthorize("isAuthenticated()")  
	@GetMapping(value="/me")
	public ResponseEntity<UserDTO> getMe(){
        UserDTO dto = service.getMe();
        return ResponseEntity.ok().body(dto);
	}
	
	
	
	

}
