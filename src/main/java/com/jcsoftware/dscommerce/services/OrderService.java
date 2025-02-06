package com.jcsoftware.dscommerce.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcsoftware.dscommerce.dtos.OrderDTO;
import com.jcsoftware.dscommerce.dtos.OrderItemDTO;
import com.jcsoftware.dscommerce.entities.Order;
import com.jcsoftware.dscommerce.entities.OrderItem;
import com.jcsoftware.dscommerce.entities.Product;
import com.jcsoftware.dscommerce.entities.enums.OrderStatus;
import com.jcsoftware.dscommerce.repositories.OrderItemRepository;
import com.jcsoftware.dscommerce.repositories.OrderRepository;
import com.jcsoftware.dscommerce.repositories.ProductRepository;
import com.jcsoftware.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserService userService;

	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {

		Optional<Order> orderO = repository.findById(id);
		Order order = orderO.orElseThrow(() -> new ResourceNotFoundException(id));
		OrderDTO dto = new OrderDTO(order);
		
		return dto;

	}

	@Transactional(readOnly = true)
	public OrderDTO insert(OrderDTO dto) {
		Order order = new Order();
		order.setMoment(Instant.now());
		order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
		order.setClient(userService.authenticated());
		for(OrderItemDTO itemDTO: dto.getItems()) {
			Product product = productRepository.getReferenceById(itemDTO.getProductId());
			OrderItem item = new OrderItem(order,product,itemDTO.getQuantity(),product.getPrice());
			order.getItems().add(item);
		}
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());
		return new OrderDTO(order);
	}
}
