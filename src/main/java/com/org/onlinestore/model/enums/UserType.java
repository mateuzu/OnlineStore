package com.org.onlinestore.model.enums;

public enum UserType {

	CLIENT("ROLE_CLIENT"),
	SUPPLIER("ROLE_SUPPLIER"),
	ADMIN("ROLE_ADMIN");
	
	private String type;

	private UserType(String type) {
		this.type = type;
	}

	public String getCode() {
		return type;
	}
	
	public static UserType fromString(String code) {
		for(UserType value : UserType.values()) {
			if(value.getCode().equalsIgnoreCase(code)) {
				return value;
			}
		}
		throw new IllegalArgumentException("Esse código é inválido!");
	}
}
