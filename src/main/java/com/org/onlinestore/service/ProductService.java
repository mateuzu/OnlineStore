package com.org.onlinestore.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.org.onlinestore.controller.dto.ProductPostRequest;
import com.org.onlinestore.model.Product;
import com.org.onlinestore.repository.ProductRepository;

@Service
public class ProductService {

	private ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public UUID postProduct(ProductPostRequest request) {
		var product = fromDto(request);
		return productRepository.save(product).getProductId();
	}
	
	public Product getProductById(String id) {
		var product = productRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
		return product;
	}
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public List<Product> getAllProductsByName(String name){
		return productRepository.findAllByNameContainingIgnoreCase(name);
	}
	
	private Product fromDto(ProductPostRequest request) {
		var product = new Product(
				UUID.randomUUID(), 
				request.name(), 
				request.description(), 
				request.price(), 
				request.quantity(),
				request.urlImg());
		
		return product;
	}
}
