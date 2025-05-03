package com.jcsoftware.dscommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jcsoftware.dscommerce.dtos.CategoryDTO;
import com.jcsoftware.dscommerce.entities.Category;
import com.jcsoftware.dscommerce.repositories.CategoryRepository;
import com.jcsoftware.dscommerce.tests.Factory;



@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {
	
	@InjectMocks
	private CategoryService service;
	private Category category;
	private List<Category> list;
	
	@Mock
	private CategoryRepository repository;
	
	@BeforeEach
	void setup() throws Exception {
		category = Factory.createCategory();
		list = new ArrayList<>();
		list.add(category);
		Mockito.when(repository.findAll()).thenReturn(list);
	}
	
	@Test
	public void findAllShouldReturnListofCategoryDTO() {
		
		List<CategoryDTO> result = service.findAll();
		Assertions.assertEquals(result.size(), 1);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.get(0).getId(), category.getId());
		Mockito.verify(repository).findAll();
	}

}
