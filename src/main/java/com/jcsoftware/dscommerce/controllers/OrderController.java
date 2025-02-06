package com.jcsoftware.dscommerce.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcsoftware.dscommerce.dtos.OrderDTO;
import com.jcsoftware.dscommerce.services.OrderService;



@RestController
@RequestMapping(value = "/orders")
public class OrderController {
	
	@Autowired
	private OrderService service;

	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value="/{id}")
	public ResponseEntity<OrderDTO> findById(@PathVariable Long id){
		OrderDTO OrderDTO = service.findById(id);
		return ResponseEntity.ok().body(OrderDTO);
	}
	
	
	
	
	

}
