package com.jcsoftware.dscommerce.dtos;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.jcsoftware.dscommerce.entities.Order;
import com.jcsoftware.dscommerce.entities.OrderItem;
import com.jcsoftware.dscommerce.entities.enums.OrderStatus;

import jakarta.validation.constraints.NotEmpty;

public class OrderDTO {

	private Long id;
	private Instant moment;
	private OrderStatus status;
	private ClientDTO client;
	private PaymentDTO payment;
	@NotEmpty(message = "Deve ser informado pelo menos um item de pedido.")
	private List<OrderItemDTO> items = new ArrayList<>();

	public OrderDTO(Long id, Instant moment, OrderStatus status, ClientDTO client, PaymentDTO payment) {
		super();
		this.id = id;
		this.moment = moment;
		this.status = status;
		this.client = client;
		this.payment = payment;
	}

	public OrderDTO(Order entity) {
		super();
		this.id = entity.getId();
		this.moment = entity.getMoment();
		this.status = entity.getOrderStatus();
		this.client = new ClientDTO(entity.getClient());
		this.payment = (entity.getPayment() == null) ? null : new PaymentDTO(entity.getPayment());
		for(OrderItem oi: entity.getItems()) {
			this.getItems().add(new OrderItemDTO(oi));
		}
	}

	public Long getId() {
		return id;
	}

	public Instant getMoment() {
		return moment;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public ClientDTO getClient() {
		return client;
	}

	public PaymentDTO getPayment() {
		return payment;
	}

	public List<OrderItemDTO> getItems() {
		return items;
	}

	public Double getTotal() {

		Double total = 0.0;

		for (OrderItemDTO oi : items) {
			total += oi.getSubTotal();
		}
		return total;
	}

}
