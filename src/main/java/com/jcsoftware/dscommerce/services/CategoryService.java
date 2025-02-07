package com.jcsoftware.dscommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcsoftware.dscommerce.dtos.CategoryDTO;
import com.jcsoftware.dscommerce.entities.Category;
import com.jcsoftware.dscommerce.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	public List<CategoryDTO> findAll() {

		List<Category> categories = repository.findAll();
		
		return categories.stream().map(x -> new CategoryDTO(x)).toList();

		
	}

	

	

	

}
