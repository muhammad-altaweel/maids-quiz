package com.maids.maidsquiz.repository;

import com.maids.maidsquiz.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query ("SELECT p FROM product p WHERE p.name LIKE %:keyword%")
	List<Product> searchProductsByName(@Param ("keyword") String keyword);
		}
