package com.jcsoftware.dscommerce.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jcsoftware.dscommerce.dtos.ProductDTO;
import com.jcsoftware.dscommerce.dtos.ProductMinDTO;
import com.jcsoftware.dscommerce.entities.Product;
import com.jcsoftware.dscommerce.repositories.CategoryRepository;
import com.jcsoftware.dscommerce.repositories.ProductRepository;
import com.jcsoftware.dscommerce.services.exceptions.IntegrityViolationException;
import com.jcsoftware.dscommerce.services.exceptions.ResourceNotFoundException;
import com.jcsoftware.dscommerce.tests.Factory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	
	@InjectMocks
	private ProductService service;
	
	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private PageImpl<Product> page;
	private Product product;
	private ProductDTO productDTO;
	private String productName;
	//private Category category;
	
	
	@Mock
	private ProductRepository repository;
	
	@Mock
	private CategoryRepository CategoryRepository;
	
	@BeforeEach
	void setup() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		dependentId = 10L;
		product = Factory.createProduct();
		productDTO = Factory.createProductDTO();
		page = new PageImpl<>(List.of(product));
		productName = "TV 50pol";
		
		
		Mockito.when(repository.searchByName(ArgumentMatchers.any(),(Pageable)ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		Mockito.when(repository.existsById(existingId)).thenReturn(true);
		Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
		Mockito.when(repository.existsById(dependentId)).thenReturn(true);
		Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
		Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException .class);
	}
	
	@Test
	public void findAllShouldReturnPage() {
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<ProductMinDTO> result = service.findAll(productName,pageable);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getSize(),1);
		Assertions.assertEquals(result.iterator().next().getName(), productName);
		Mockito.verify(repository).searchByName(productName,pageable);
		
	}
	
	@Test
	public void insertShouldReturnProductDTO() {
		
		ProductDTO result = service.insert(productDTO);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(),existingId);
		
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		
		ProductDTO result = service.findById(existingId);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(),existingId);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionOWhenIdDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class,()->{
			service.findById(nonExistingId);
		});
		
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(()->{
			service.delete(existingId);
		});
		
		Mockito.verify(repository,Mockito.times(1)).deleteById(existingId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class,()->{
			service.delete(nonExistingId);
		});
	}
	
	@Test
	public void deleteShouldThrowIntegrityViolationExceptionWhenDependentid() {
		Assertions.assertThrows(IntegrityViolationException.class,()->{
			service.delete(dependentId);
		});
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionOWhenIdDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class,()->{
			service.update(nonExistingId, productDTO);
		});
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {
		ProductDTO result = service.update(existingId, productDTO);
		Assertions.assertNotNull(result);
	}
	
	
	
}
