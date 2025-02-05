package com.jcsoftware.dscommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcsoftware.dscommerce.dtos.CategoryDTO;
import com.jcsoftware.dscommerce.dtos.ProductDTO;
import com.jcsoftware.dscommerce.dtos.ProductMinDTO;
import com.jcsoftware.dscommerce.entities.Category;
import com.jcsoftware.dscommerce.entities.Product;
import com.jcsoftware.dscommerce.repositories.ProductRepository;
import com.jcsoftware.dscommerce.services.exceptions.IntegrityViolationException;
import com.jcsoftware.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public Page<ProductMinDTO> findAll(String name,Pageable pageable) {

		Page<Product> products = repository.searchByName(name,pageable);
		
		return products.map(x -> new ProductMinDTO(x));

		
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {

		Optional<Product> productO = repository.findById(id);
		Product product = productO.orElseThrow(() -> new ResourceNotFoundException(id));
		ProductDTO dto = new ProductDTO(product);
		
		return dto;

	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {

		Product newProduct = new Product();
		copyDtoToEntity(dto, newProduct);
		
		newProduct = repository.save(newProduct);

		return new ProductDTO(newProduct);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {

		try {
			Product product = repository.getReferenceById(id);
			copyDtoToEntity(dto, product);
			product = repository.save(product);
			return new ProductDTO(product);
		} catch (EntityNotFoundException e) {
			throw (new ResourceNotFoundException(id));
		}

	}

	public void delete(Long id) {

		try {

			if (repository.existsById(id)) {
				repository.deleteById(id);
			} else {
				throw (new ResourceNotFoundException(id));
			}
		}

		catch (DataIntegrityViolationException e) {
			throw (new IntegrityViolationException(id));
		}

	}
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {

		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		entity.getCategories().clear();
		for(CategoryDTO catDTO: dto.getCategories()) {
			Category cat = new Category();
			cat.setId(catDTO.getId());
			entity.getCategories().add(cat);
		}
	}
/*
	private void updateData(ProductDTO source, Product target) {

		target.setName(source.getName());
		target.setDescription(source.getDescription());
		target.setPrice(source.getPrice());
		target.setImgUrl(source.getImgUrl());
	}
	*/

}
