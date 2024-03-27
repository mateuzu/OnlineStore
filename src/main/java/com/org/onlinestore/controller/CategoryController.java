package com.org.onlinestore.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.onlinestore.controller.dto.CategoryPostRequest;
import com.org.onlinestore.model.Category;
import com.org.onlinestore.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {

	private CategoryService categoryService;

	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@PostMapping
	public ResponseEntity<Void> postCategory(@RequestBody @Valid CategoryPostRequest categoryPostRequest){
		var id = categoryService.postCategory(categoryPostRequest);
		return ResponseEntity.created(URI.create("/v1/categorys/" + id.toString())).build();
	}
	
	@GetMapping("/get/{categoryId}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("categoryId") String categoryId){
		var category = categoryService.getCategoryById(categoryId);
		return ResponseEntity.ok().body(category);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Category>> getAllCategories(){
		var list = categoryService.getAllCategories();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/all/{name}")
	public ResponseEntity<List<Category>> getAllCategoriesByName(@PathVariable("name") String name){
		var list = categoryService.getAllCategoriesByName(name);
		return ResponseEntity.ok().body(list);
	}
}
