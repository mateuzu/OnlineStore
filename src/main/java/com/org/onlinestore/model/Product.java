package com.org.onlinestore.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_PRODUCT")
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "productId")
	private UUID productId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "urlImg")
	private String urlImg;
	
	@ManyToOne
	private Category category;
	
	@OneToMany(mappedBy = "orderItemId.product")
	private Set<OrderItem> items = new HashSet<>();
	
	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(UUID productId, String name, String description, Double price, String urlImg) {
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.urlImg = urlImg;
	}

	public UUID getProductId() {
		return productId;
	}

	public void setProductId(UUID productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@JsonIgnore
	public Set<Order> getItems() {
		Set<Order> set = new HashSet<>();
		
		for (OrderItem x : items) {
			set.add(x.getOrder());
		}
		return set;
	}
	
	
	
}
