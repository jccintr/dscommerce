package com.jcsoftware.dscommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcsoftware.dscommerce.dtos.UserDTO;
import com.jcsoftware.dscommerce.dtos.UserRegisterDTO;
import com.jcsoftware.dscommerce.entities.Role;
import com.jcsoftware.dscommerce.entities.User;
import com.jcsoftware.dscommerce.projections.UserDetailsProjection;
import com.jcsoftware.dscommerce.repositories.RoleRepository;
import com.jcsoftware.dscommerce.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository repository;
	
	@Autowired
	RoleRepository roleRepository;
	
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);

		if (result.size() == 0) {
			throw new UsernameNotFoundException("User not found");
		}

		User user = new User();
		user.setEmail(username);
		user.setPassword(result.get(0).getPassword());
		for (UserDetailsProjection projection : result) {
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}

		return user;
	}

	protected User authenticated() {

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
			String username = jwtPrincipal.getClaim("username");
			return repository.findByEmail(username).get();
		} catch (Exception e) {
			throw new UsernameNotFoundException("Enmail not found.");
		}

	}
	

	@Transactional(readOnly=true)
	public UserDTO getMe() {

		User user = authenticated();
		return new UserDTO(user);
	}
	
	
	@Transactional
	public UserDTO register(UserRegisterDTO dto) {
		
		User newUser = new User();
		newUser.setName(dto.getName()); 
		newUser.setEmail(dto.getEmail());
		newUser.setPhone(dto.getPhone());
		newUser.setBirthDate(dto.getBirthDate());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
       	newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
       	Role roleClient = roleRepository.getReferenceById(1L);
       	newUser.addRole(roleClient);
		newUser = repository.save(newUser);
		
		return new UserDTO(newUser);
		
	}

}
