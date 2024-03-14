package com.org.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.onlinestore.model.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
