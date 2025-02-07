package com.jcsoftware.dscommerce.controllers;



import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jcsoftware.dscommerce.dtos.UserDTO;
import com.jcsoftware.dscommerce.dtos.UserRegisterDTO;
import com.jcsoftware.dscommerce.services.UserService;

import jakarta.validation.Valid;



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
	
	@PostMapping(value="/register")
	public ResponseEntity<UserDTO> register(@Valid @RequestBody UserRegisterDTO dto){
        UserDTO userDto = service.register(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(userDto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(userDto);
      //  return ResponseEntity.ok().body(userDto);
	}
	
	

}
