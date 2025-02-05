package com.jcsoftware.dscommerce.dtos;

import com.jcsoftware.dscommerce.entities.Product;

public class ProductMinDTO {
	
	private Long id;
	private String name;
	private Double price;
	private String imgUrl;

	public ProductMinDTO() {
		
	}

	public ProductMinDTO(Product entity) {
		super();
		this.id = entity.getId();
		this.name = entity.getName();
		this.price = entity.getPrice();
		this.imgUrl = entity.getImgUrl();
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
	
	
	
	
}
