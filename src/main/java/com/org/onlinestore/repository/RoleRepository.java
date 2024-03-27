package com.org.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.onlinestore.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByNameIgnoreCase(String name);
}
