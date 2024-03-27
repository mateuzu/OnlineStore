package com.org.onlinestore.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.onlinestore.controller.dto.ProductPostRequest;
import com.org.onlinestore.model.Product;
import com.org.onlinestore.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

	private ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@PostMapping
	@PreAuthorize("hasAnyAuthority('SCOPE_admin', 'SCOPE_supplier')")
	public ResponseEntity<Void> postProduct(@RequestBody @Valid ProductPostRequest request){
		var id = productService.postProduct(request);
		return ResponseEntity.created(URI.create("/v1/product/" + id.toString())).build();	
	}
	
	@GetMapping("/get/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable("productId") String productId){
		var product = productService.getProductById(productId);
		return ResponseEntity.ok().body(product);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProducts(){
		var list = productService.getAllProducts();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/all/{name}")
	public ResponseEntity<List<Product>> getAllProductsByName(@PathVariable("name") String name){
		var list = productService.getAllProductsByName(name);
		return ResponseEntity.ok().body(list);
	}
}
