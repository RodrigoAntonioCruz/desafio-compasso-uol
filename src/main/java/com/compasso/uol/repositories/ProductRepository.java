package com.compasso.uol.repositories;

import java.util.List;

import com.compasso.uol.dto.ProductDTO;
import com.compasso.uol.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rodrigo da Cruz
 * @version 1.0
 * @since 2021-02-25
 * 
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT p from Product p WHERE p.price BETWEEN ?1 AND ?2 AND concat(LOWER(p.name) ,' ', LOWER(p.description)) LIKE LOWER(concat('%', concat(?3, '%')))")
	public List<ProductDTO> searchIgnoreCase(Double min_price, Double max_price, String q);
}
