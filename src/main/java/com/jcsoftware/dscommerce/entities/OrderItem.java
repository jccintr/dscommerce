package com.jcsoftware.dscommerce.entities;



import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jcsoftware.dscommerce.entities.pk.OrderItemPK;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private OrderItemPK id = new OrderItemPK();
	private Integer quantity;
	private Double price;
	
	public OrderItem() {
		
	}

	public OrderItem(Order order,Product product,Integer quantity, Double price) {
		super();
		id.setOrder(order);
		id.setProduct(product);
		this.quantity = quantity;
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@JsonIgnore
	public Order getOrder() {
		return id.getOrder();
	}
	
	public void setOrder(Order order) {
		id.setOrder(order);
	}
	
	public Product getProduct() {
		return id.getProduct();
	}
	
	public void setProduct(Product product) {
		id.setProduct(product);
	}
	
	public Double getSubTotal() {
		return quantity * price;
	}
	
	
}
