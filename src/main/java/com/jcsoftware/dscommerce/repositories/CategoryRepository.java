package com.jcsoftware.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcsoftware.dscommerce.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{

}
