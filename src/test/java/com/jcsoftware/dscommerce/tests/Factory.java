package com.jcsoftware.dscommerce.tests;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.jcsoftware.dscommerce.dtos.CategoryDTO;
import com.jcsoftware.dscommerce.dtos.ProductDTO;
import com.jcsoftware.dscommerce.entities.Category;
import com.jcsoftware.dscommerce.entities.Order;
import com.jcsoftware.dscommerce.entities.OrderItem;
import com.jcsoftware.dscommerce.entities.Product;
import com.jcsoftware.dscommerce.entities.Role;
import com.jcsoftware.dscommerce.entities.User;
import com.jcsoftware.dscommerce.entities.enums.OrderStatus;
import com.jcsoftware.dscommerce.projections.UserDetailsProjection;

public class Factory {
	
	public static Category createCategory() {
		return new Category(1L,"Eletrônicos");
	}
	
	public static CategoryDTO createCategoryDTO(Category entity) {
		  return new CategoryDTO(entity);
	}
	
	public static Product createProduct() {
		Product product = new Product(1L,"TV 50pol","Super tv colorida e fininha", 300.0, "http://www");
		product.getCategories().add(new Category(1L,"Eletrônicos"));
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product);
	}
	
	public static User createClientUser() {
		var user =  new User(1L,"Maria","maria@gmail.com","3641-1191",LocalDate.parse("2002-01-01"),"$2a$10$Jb9R4LPPhJu9k7d5VaCjSeGLOB.Kbwr3dwf5r2TMA63SgZdDmwV8W");
	    user.addRole(new Role(1L,"ROLE_CLIENT"));
	    return user;
	}
	
	public static User createAdminUser() {
		var user = new User(2L,"Alex","alex@gmail.com","3641-1191",LocalDate.parse("2002-01-01"),"$2a$10$Jb9R4LPPhJu9k7d5VaCjSeGLOB.Kbwr3dwf5r2TMA63SgZdDmwV8W");
	    user.addRole(new Role(2L,"ROLE_ADMIN"));
	    return user;
	}
	
	public static User createCustomClientUser(Long id,String userName) {
		var user = new User(id,"Maria",userName,"3641-1191",LocalDate.parse("2002-01-01"),"$2a$10$Jb9R4LPPhJu9k7d5VaCjSeGLOB.Kbwr3dwf5r2TMA63SgZdDmwV8W");
		 user.addRole(new Role(1L,"ROLE_CLIENT"));
	    return user;
	}
	
	public static User createCustomAdminUser(Long id,String userName) {
		var user = new User(id,"Alex",userName,"3641-1191",LocalDate.parse("2002-01-01"),"$2a$10$Jb9R4LPPhJu9k7d5VaCjSeGLOB.Kbwr3dwf5r2TMA63SgZdDmwV8W");
		 user.addRole(new Role(2L,"ROLE_ADMIN"));
	    return user;
	}
	
	public static List<UserDetailsProjection> createCustomClientuser(String username){
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username,"123",1L,"ROLE_CLIENT"));
		return list;
	}
	
	public static List<UserDetailsProjection> createCustomAdminuser(String username){
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username,"123",2L,"ROLE_ADMIN"));
		return list;
	}
	
	public static List<UserDetailsProjection> createCustomAdminClientuser(String username){
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username,"123",1L,"ROLE_CLIENT"));
		list.add(new UserDetailsImpl(username,"123",2L,"ROLE_ADMIN"));
		return list;
	}
	
	public static Order createOrder(User client) {
		
	   Order order = new Order(1L,Instant.now(),OrderStatus.WAITING_PAYMENT,client);
	   Product product = createProduct();
	   OrderItem orderItem = new OrderItem(order,product,2,20.0);
	   order.getItems().add(orderItem);
	   return order;
			   
	}
}

class UserDetailsImpl implements UserDetailsProjection {

	private String username;
	private String password;
	private Long roleId;
	private String authority;
	
	public UserDetailsImpl() {
		
	}
	
	public UserDetailsImpl(String username, String password, Long roleId, String authority) {
		
		this.username = username;
		this.password = password;
		this.roleId = roleId;
		this.authority = authority;
	}

	@Override
	public String getUsername() {
		
		return username;
	}

	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public Long getRoleId() {
		
		return roleId;
	}

	@Override
	public String getAuthority() {
		
		return authority;
	}
	
}
