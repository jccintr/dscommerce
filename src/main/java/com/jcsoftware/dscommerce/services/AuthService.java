package com.jcsoftware.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcsoftware.dscommerce.entities.User;
import com.jcsoftware.dscommerce.services.exceptions.ForbiddenException;

@Service
public class AuthService {
	
	@Autowired
	UserService userService;
	
	public void validateSelfOrAdmin(Long userId,Long resource) {
		
		User me = userService.authenticated();
		
		if(!me.hasRole("ROLE_ADMIN") && !me.getId().equals(userId)) {
			throw new ForbiddenException(resource);
		}
		
		
		
		
	}

}
