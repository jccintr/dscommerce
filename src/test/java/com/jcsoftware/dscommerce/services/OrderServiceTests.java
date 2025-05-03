package com.jcsoftware.dscommerce.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jcsoftware.dscommerce.dtos.OrderDTO;
import com.jcsoftware.dscommerce.entities.Order;
import com.jcsoftware.dscommerce.entities.OrderItem;
import com.jcsoftware.dscommerce.entities.Product;
import com.jcsoftware.dscommerce.entities.User;
import com.jcsoftware.dscommerce.repositories.OrderItemRepository;
import com.jcsoftware.dscommerce.repositories.OrderRepository;
import com.jcsoftware.dscommerce.repositories.ProductRepository;
import com.jcsoftware.dscommerce.services.exceptions.ForbiddenException;
import com.jcsoftware.dscommerce.services.exceptions.ResourceNotFoundException;
import com.jcsoftware.dscommerce.tests.Factory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class OrderServiceTests {
	
	@InjectMocks
	private OrderService service;
	
	@Mock
	private OrderRepository repository;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private OrderItemRepository orderItemRepository;
	
	@Mock
	private AuthService authService;
	
	@Mock
	private UserService userService;
	
	private Long existingId,nonExistingId;
	private Long existingOrderId,nonExistingOrderId;
	private Long existingProductId,nonExistingProductId;
	private Product product;


	private Order order;
	private OrderDTO orderDTO;
	private User admin,client;
	
	@BeforeEach
	void setup() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		existingProductId = 1L;
		nonExistingProductId = 1000L;
		existingOrderId = 1L;
		nonExistingOrderId = 1000L;
		
		admin = Factory.createCustomAdminUser(1L,"alex@gmail.com");
		client = Factory.createCustomClientUser(2L, "maria@gmail.com");
		
		order  = Factory.createOrder(client);
		
		orderDTO = new OrderDTO(order);
		
		product = Factory.createProduct();
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(order));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.save(Mockito.any())).thenReturn(order);
		
		
		Mockito.when(productRepository.getReferenceById(existingProductId)).thenReturn(product);
		Mockito.when(productRepository.getReferenceById(nonExistingProductId)).thenThrow(EntityNotFoundException .class);
	  
		Mockito.when(orderItemRepository.saveAll(any())).thenReturn(new ArrayList<>(order.getItems()));
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionOWhenIdDoesNotExists() {
		Mockito.doNothing().when(authService).validateSelfOrAdmin(any(),any());
		Assertions.assertThrows(ResourceNotFoundException.class,()->{
			service.findById(nonExistingId);
		});
		
	}
	
	@Test
	public void findByIdShouldReturnOrderDTOWhenAdminLoggedAndIdExists() {
		
	    Mockito.doNothing().when(authService).validateSelfOrAdmin(any(),any());
		OrderDTO result = service.findById(existingId);
		
		Assertions.assertDoesNotThrow(()->{
			authService.validateSelfOrAdmin(order.getClient().getId(),order.getId());
		});
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(),existingId);
	}
	
	@Test
	public void findByIdShouldReturnOrderDTOWhenSelfClientLoggedAndIdExists() {
		
	    Mockito.doNothing().when(authService).validateSelfOrAdmin(any(),any());
		OrderDTO result = service.findById(existingId);
		
		Assertions.assertDoesNotThrow(()->{
			authService.validateSelfOrAdmin(order.getClient().getId(),order.getId());
		});
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(),existingId);
	}
	
	@Test
	public void findByIdShouldThrowForbidenExceptionWhenClientLoggedAccessOthersOrders() {
		
		Mockito.doThrow(ForbiddenException.class).when(authService).validateSelfOrAdmin(any(),any());
		
		Assertions.assertThrows(ForbiddenException.class,()->{
			OrderDTO result = service.findById(existingId);
		});
		
		
	}
	
	@Test
	public void insertShouldReturnOrderDTOWhenUserLogged() {
		
		Mockito.when(userService.authenticated()).thenReturn(admin);
		OrderDTO result = service.insert(orderDTO);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(),existingOrderId );
	}
	
	@Test
	public void insertShoudThrowUsarnameNotfoundExceptionWhenNotLoggedUser() {
		Mockito.doThrow(UsernameNotFoundException.class).when(userService).authenticated();
		order.setClient(new User());
		orderDTO= new OrderDTO(order);
		Assertions.assertThrows(UsernameNotFoundException.class,()->{
			OrderDTO result = service.insert(orderDTO);
		});
	}
	
	@Test
	public void insertShouldThrowsEntityNotFoundExceptionWhenProductIdDoesNotExists() {
		Mockito.when(userService.authenticated()).thenReturn(client);
		product.setId(nonExistingProductId);
		OrderItem orderItem = new OrderItem(order,product,1,20.0);
		order.getItems().add(orderItem);
		orderDTO = new OrderDTO(order);
		Assertions.assertThrows(EntityNotFoundException.class,()->{
			OrderDTO result = service.insert(orderDTO);
		});
	}
}
