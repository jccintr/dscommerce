package com.jcsoftware.dscommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcsoftware.dscommerce.dtos.OrderDTO;
import com.jcsoftware.dscommerce.entities.Order;
import com.jcsoftware.dscommerce.repositories.OrderRepository;
import com.jcsoftware.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;

	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {

		Optional<Order> orderO = repository.findById(id);
		Order order = orderO.orElseThrow(() -> new ResourceNotFoundException(id));
		OrderDTO dto = new OrderDTO(order);
		
		return dto;

	}
}
