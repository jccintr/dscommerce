package com.jcsoftware.dscommerce.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcsoftware.dscommerce.dtos.ProductDTO;
import com.jcsoftware.dscommerce.services.ProductService;



@RestController
@RequestMapping(value = "/products")
public class ProductController {
	
	@Autowired
	private ProductService service;

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable){
        Page<ProductDTO> products = service.findAll(pageable);
        return ResponseEntity.ok().body(products);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
		ProductDTO productDTO = service.findById(id);
		return ResponseEntity.ok().body(productDTO);
	}

}
