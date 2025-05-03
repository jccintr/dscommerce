package com.jcsoftware.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcsoftware.dscommerce.entities.OrderItem;
import com.jcsoftware.dscommerce.entities.pk.OrderItemPK;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,OrderItemPK>{ 

}
