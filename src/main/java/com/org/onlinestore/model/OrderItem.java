package com.org.onlinestore.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.org.onlinestore.model.pk.OrderItemPK;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_ORDER_ITEM")
public class OrderItem implements Serializable{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@Column(name = "orderItemId")
	private OrderItemPK orderItemId = new OrderItemPK();
	
	private Integer quantity;
	private Double price;
	
	public OrderItem() {
		// TODO Auto-generated constructor stub
	}

	public OrderItem(Order order, Product product, OrderItemPK orderItemId, Integer quantity, Double price) {
		orderItemId.setOrder(order);
		orderItemId.setProduct(product);
		this.orderItemId = orderItemId;
		this.quantity = quantity;
		this.price = price;
	}

	@JsonIgnore
	public Order getOrder() {
		return orderItemId.getOrder();
	}
	
	public void setOrder(Order order){
		orderItemId.setOrder(order);
	}
	
	public Product getProduct() {
		return orderItemId.getProduct();
	}
	
	public void setProduct(Product product) {
		orderItemId.setProduct(product);
	}
	
	public OrderItemPK getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(OrderItemPK orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
