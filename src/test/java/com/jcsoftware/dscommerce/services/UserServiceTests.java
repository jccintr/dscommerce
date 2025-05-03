package com.jcsoftware.dscommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jcsoftware.dscommerce.dtos.UserDTO;
import com.jcsoftware.dscommerce.entities.User;
import com.jcsoftware.dscommerce.projections.UserDetailsProjection;
import com.jcsoftware.dscommerce.repositories.UserRepository;
import com.jcsoftware.dscommerce.tests.Factory;
import com.jcsoftware.dscommerce.util.CustomUserUtil;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {
	
	@InjectMocks
	private UserService service;
	private String existingUserName;
	private String nonExistingUserName;
	private User user;
	
	private List<UserDetailsProjection> userDetails;
	
	@Mock
	private UserRepository repository;
	@Mock
	private CustomUserUtil customUserUtil;
	
	@BeforeEach
	void setup() throws Exception {
		existingUserName = "maria@gmail.com";
		nonExistingUserName = "user@gmail.com";
		user = Factory.createCustomClientUser(1L, existingUserName);
		userDetails = Factory.createCustomClientuser(existingUserName);
		Mockito.when(repository.searchUserAndRolesByEmail(existingUserName)).thenReturn(userDetails);
		Mockito.when(repository.searchUserAndRolesByEmail(nonExistingUserName)).thenReturn(new ArrayList<>());
		Mockito.when(repository.findByEmail(existingUserName)).thenReturn(Optional.of(user));
		Mockito.when(repository.findByEmail(nonExistingUserName)).thenReturn(Optional.empty());
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(user);
		
	}
	
	@Test
	public void loadUserByEmailShoudReturnUserDetailsWhenUserExists() {
		UserDetails result = service.loadUserByUsername(existingUserName);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getUsername(),existingUserName);
	}
	
	@Test
	public void loadUserByEmailShouldThrowUserNotFoundExceptionWhenUserDoesNotExists() {
	
		
		Assertions.assertThrows(UsernameNotFoundException.class,()->{
			service.loadUserByUsername(nonExistingUserName);
		});
	}
	
	@Test
	public void authenticatedShoulReturnUserWhenUserExists() {
		
		Mockito.when(customUserUtil.getLoggedUserName()).thenReturn(existingUserName);
		User result = service.authenticated();
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getUsername(),existingUserName);
	}
	
	@Test
	public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
		
		Mockito.doThrow(ClassCastException.class).when(customUserUtil).getLoggedUserName();
		Assertions.assertThrows(UsernameNotFoundException.class,()->{
			service.authenticated();
		});
		
	}
	
	@Test
	public void getMeShouldReturnUserDTOWhenUserAuthenticated() {
		
		
		UserService spyService = Mockito.spy(service);
		Mockito.doReturn(user).when(spyService).authenticated();
		UserDTO result = spyService.getMe();
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getEmail(),existingUserName);
		
	}
	
	@Test
	public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserNotAuthenticated() {
		UserService spyService = Mockito.spy(service);
		Mockito.doThrow(UsernameNotFoundException.class).when(spyService).authenticated();
		Assertions.assertThrows(UsernameNotFoundException.class,()->{
			 spyService.getMe();
		});
	}
	
	
}
