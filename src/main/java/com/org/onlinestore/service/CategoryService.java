package com.org.onlinestore.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.org.onlinestore.controller.dto.CategoryPostRequest;
import com.org.onlinestore.model.Category;
import com.org.onlinestore.repository.CategoryRepository;

@Service
public class CategoryService {

	private CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public UUID postCategory(CategoryPostRequest categoryPostRequest) {
		var category = fromDto(categoryPostRequest);
		return categoryRepository.save(category).getCategoryId();
	}
	
	public Category getCategoryById(String id) {
		var category = categoryRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada!"));
		return category;
	}
	
	public List<Category> getAllCategories(){
		return categoryRepository.findAll();
	}
	
	public List<Category> getAllCategoriesByName(String name){
		return categoryRepository.findAllByNameContainingIgnoreCase(name);
	}
	
	private Category fromDto(CategoryPostRequest categoryPostRequest) {
		var category = new Category(
				UUID.randomUUID(), 
				categoryPostRequest.name(), 
				categoryPostRequest.description(), 
				categoryPostRequest.urlImg());
		
		return category;
	}
}
