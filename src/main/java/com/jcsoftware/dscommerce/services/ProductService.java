package com.jcsoftware.dscommerce.services;


import java.util.Optional;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jcsoftware.dscommerce.dtos.ProductDTO;
import com.jcsoftware.dscommerce.entities.Product;
import com.jcsoftware.dscommerce.repositories.ProductRepository;


@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public Page<ProductDTO> findAll(Pageable pageable) {

		Page<Product> products = repository.findAll(pageable);
		// List<ProductDTO> dtos = new ArrayList<>();
		return products.map(x -> new ProductDTO(x));

		/*
		 * for(Product p: products) { ProductDTO dto = new ProductDTO();
		 * BeanUtils.copyProperties(p, dto); dtos.add(dto); } return dtos;
		 */
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {

		Optional<Product> productO = repository.findById(id);
		Product product = productO.get();
		ProductDTO dto = new ProductDTO();
		BeanUtils.copyProperties(product, dto);
		return dto;

	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {

		Product newProduct = new Product();
		BeanUtils.copyProperties(dto, newProduct);
		newProduct = repository.save(newProduct);
		
		return new ProductDTO(newProduct);
	}
	 @Transactional
	 public ProductDTO update(Long id,ProductDTO dto) {
		 
		    
		  //  try {	
		     Product product = repository.getReferenceById(id);
		     
		     updateData(dto,product);
		     product = repository.save(product);
		     
		     return new ProductDTO(product);
		  //  } 
		  //  catch (EntityNotFoundException e) {
		   // 	e.printStackTrace();
		    	//throw(new ResourceNotFoundException(id));
		  //  }
	    
	    }
	 
	 public void delete(Long id) {
		 
		 repository.deleteById(id);
	 
	 }
	 
	 private void updateData(ProductDTO source,Product target) {
		 
		 target.setName(source.getName());
		 target.setDescription(source.getDescription());
		 target.setPrice(source.getPrice());
		 target.setImgUrl(source.getImgUrl());
	 }

}
