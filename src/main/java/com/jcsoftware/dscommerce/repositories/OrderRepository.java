package com.jcsoftware.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcsoftware.dscommerce.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{ 

}
