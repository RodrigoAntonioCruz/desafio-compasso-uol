package com.compasso.uol.repositories.specs.products;

import com.compasso.uol.entities.Product;
import org.springframework.data.jpa.domain.Specification;

/**
*
* @author Rodrigo da Cruz
* @version 1.0
* @since 2021-03-03
* 
*/

public class ProductSpecification {

	public static Specification<Product> params(String q, Double min_price, Double max_price){
		
    	if(!q.isEmpty() && min_price != null && max_price != null) {
    		return (root, query, builder)->builder.and(builder.like(builder.upper(
                                           builder.concat(root.get("name"), root.get("description"))), "%" + q.toUpperCase() + "%"),
                                           builder.between(root.get("price"), min_price, max_price ));
    	}
    	else if(!q.isEmpty() && min_price != null && max_price == null) {
    		return (root, query, builder)->builder.and(builder.like(builder.upper(
                                           builder.concat(root.get("name"), root.get("description"))), "%" + q.toUpperCase() + "%"),
                                           builder.greaterThanOrEqualTo(root.get("price"), min_price));
    	} 
    	else if(!q.isEmpty() && min_price == null && max_price != null) {
    		return (root, query, builder)->builder.and(builder.like(builder.upper(
                                           builder.concat(root.get("name"), root.get("description"))), "%" + q.toUpperCase() + "%"),
                                           builder.lessThanOrEqualTo(root.get("price"), max_price ));
    	}
    	else if(!q.isEmpty() && min_price == null && max_price == null) {
            return (root, query, builder)->builder.like(builder.upper(
                                           builder.concat(root.get("name"), root.get("description"))), "%" + q.toUpperCase() + "%");
    	}
    	else if(q.isEmpty() && min_price != null && max_price != null) {
    		return (root, query, builder)->builder.between(root.get("price"), min_price, max_price );
    	}
    	else if(q.isEmpty() && min_price != null && max_price == null) {
    		return (root, query, builder)->builder.greaterThanOrEqualTo(root.get("price"), min_price);
    	}
    	else if(q.isEmpty() && min_price == null && max_price != null) {
    		return (root, query, builder)->builder.lessThanOrEqualTo(root.get("price"), max_price );
    	}
    	else {
    		return null;
    	}  
	}
}
