package com.jcsoftware.dscommerce.dtos;

import java.util.ArrayList;
import java.util.List;

import com.jcsoftware.dscommerce.entities.Category;
import com.jcsoftware.dscommerce.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {

	private Long id;

	@NotBlank(message = "Campo requerido")
	@Size(min = 3, max = 80, message = "O campo deve ter mais de 3 e menos de 80 caracteres")
	private String name;
	@Size(min = 10, message = "A descrição deve ter mais do que 10 caracteres.")
	private String description;
	@NotNull(message="Campo requerido")
	@Positive(message = "O preço deve ser maior do que zero.")
	private Double price;
	private String imgUrl;
	@NotEmpty(message = "Deve ser informada pelo menos uma categoria.")
	private List<CategoryDTO> categories = new ArrayList<>();

	public ProductDTO() {

	}

	public ProductDTO(Product entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.imgUrl = entity.getImgUrl();
		for(Category cat: entity.getCategories()) {
			this.categories.add(new CategoryDTO(cat));
		}
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
	
	

}
