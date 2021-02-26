package com.compasso.uol.repositories;

import com.compasso.uol.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
