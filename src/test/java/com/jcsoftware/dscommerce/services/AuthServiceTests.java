package com.jcsoftware.dscommerce.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jcsoftware.dscommerce.entities.User;
import com.jcsoftware.dscommerce.services.exceptions.ForbiddenException;
import com.jcsoftware.dscommerce.tests.Factory;

@ExtendWith(SpringExtension.class)
public class AuthServiceTests {
	
	@InjectMocks
	AuthService service;
	
	@Mock
	UserService userService;
	
	private User admin,selfClient,otherClient;
	
	
	@BeforeEach
	void setup() throws Exception {
		admin = Factory.createAdminUser();
		selfClient = Factory.createCustomClientUser(1L, "bob@gmail.com");
		otherClient = Factory.createCustomClientUser(2L, "ana@gmail.com");

    }
	
	@Test
	public void validateSelfOrAdminShouldDoNothingWhenAdminLogged() {
		
		Mockito.when(userService.authenticated()).thenReturn(admin);
		Long userId = admin.getId();
		Assertions.assertDoesNotThrow(()->{
			service.validateSelfOrAdmin(userId, 10L);
		});
	}
	
	@Test
	public void validateSelfOrAdminShouldDoNothingWhenClientLoggedAccessOwnOrder() {
		Mockito.when(userService.authenticated()).thenReturn(selfClient);
		Long userId = selfClient.getId();
		Assertions.assertDoesNotThrow(()->{
			service.validateSelfOrAdmin(userId, 10L);
		});
	}
	@Test
	public void validateSelfOrAdminShouldThrowForbidenExceptionWhenClientLoggedAccessOthersOrders() {
		Mockito.when(userService.authenticated()).thenReturn(otherClient);
		Long userId = selfClient.getId();
		Assertions.assertThrows(ForbiddenException.class,()->{
			service.validateSelfOrAdmin(userId, 10L);
		});
	}
	
}
