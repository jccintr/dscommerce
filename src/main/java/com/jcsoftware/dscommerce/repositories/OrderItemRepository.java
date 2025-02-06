package com.jcsoftware.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcsoftware.dscommerce.entities.OrderItem;
import com.jcsoftware.dscommerce.entities.pk.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem,OrderItemPK>{ 

}
