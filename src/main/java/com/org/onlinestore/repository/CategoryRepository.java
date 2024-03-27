package com.org.onlinestore.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.org.onlinestore.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

	List<Category> findAllByNameContainingIgnoreCase(@Param("name") String name);
}
