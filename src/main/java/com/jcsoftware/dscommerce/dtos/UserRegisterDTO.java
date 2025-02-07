package com.jcsoftware.dscommerce.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO{
	
	private Long id;
	@NotBlank(message = "Campo requerido")
	@Size(min = 3, message = "O campo deve ter pelo menos 3 caracteres")
	private String name;
	@NotBlank(message = "Campo requerido")
	@Email(message="Email inválido",regexp = "/^[a-z0-9.]+@[a-z0-9]+\\.[a-z]+\\.([a-z]+)?$/i")
	private String email;
	private String phone;
	@Past(message="Data de nascimento deve ser inferior a data atual")
	private LocalDate birthDate;
	@NotBlank(message = "Campo requerido")
	@Size(min = 6, message = "O campo deve ter pelo menos 6 caracteres")
	private String password;
	
	private List<String> roles = new ArrayList<>();

	public UserRegisterDTO(Long id, String name, String email, String phone, String birthDate,String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.birthDate = LocalDate.parse(birthDate);
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	
	
	
	
	

}
