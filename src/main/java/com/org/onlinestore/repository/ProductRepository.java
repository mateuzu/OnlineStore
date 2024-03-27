package com.org.onlinestore.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.org.onlinestore.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

	List<Product> findAllByNameContainingIgnoreCase(@Param("name")String name);
}
