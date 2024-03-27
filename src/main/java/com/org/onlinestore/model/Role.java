package com.org.onlinestore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_ROLE")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long roleId;
	
	@Column(name = "name")
	private String name;
	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public enum Values {
		
		CLIENT(1L),
		SUPPLIER(2L),
		ADMIN(3L);
		
		Long roleId;
		
		Values(Long roleId){
			this.roleId = roleId;
		}
		
		public Long getRoleId() {
			return roleId;
		}
		
		public static Values valueOf(Long roleId) {
			for(Values value : Values.values()) {
				if(value.getRoleId() == roleId) {
					return value;
				}
			}
			throw new IllegalArgumentException("Esse código é inválido!");
		}
	}
}
