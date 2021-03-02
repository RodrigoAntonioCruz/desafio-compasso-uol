package com.compasso.uol.repositories.specs.products;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.criteria.Root;
import com.compasso.uol.entities.Product;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;
import com.compasso.uol.repositories.specs.SearchCriteria;
import com.compasso.uol.repositories.specs.SearchOperation;

/**
*
* @author Rodrigo da Cruz
* @version 1.0
* @since 2021-03-02
* 
*/

public class ProductSpecification implements Specification<Product> {

	private static final long serialVersionUID = 1L;
	
	private List<SearchCriteria> list;
    
	public ProductSpecification(String q, Double min_price, Double max_price) {

		this.list = new ArrayList<>();

		if (!q.isEmpty() && min_price != null && max_price != null) {
			add(new SearchCriteria("name", "description", q, SearchOperation.MATCH));
			add(new SearchCriteria("price", max_price, SearchOperation.LESS_THAN_EQUAL));
			add(new SearchCriteria("price", min_price, SearchOperation.GREATER_THAN_EQUAL));
		} 
		else if (!q.isEmpty() && min_price != null && max_price == null) {
			add(new SearchCriteria("name", "description", q, SearchOperation.MATCH));
			add(new SearchCriteria("price", min_price, SearchOperation.GREATER_THAN_EQUAL));
		} 
		else if (!q.isEmpty() && min_price == null && max_price != null) {
			add(new SearchCriteria("name", "description", q, SearchOperation.MATCH));
			add(new SearchCriteria("price", max_price, SearchOperation.LESS_THAN_EQUAL));
		} 
		else if (!q.isEmpty() && min_price == null && max_price == null) {
			add(new SearchCriteria("name", "description", q, SearchOperation.MATCH));
		} 
		else if (q.isEmpty() && min_price != null && max_price != null) {
			add(new SearchCriteria("price", min_price, SearchOperation.GREATER_THAN_EQUAL));
			add(new SearchCriteria("price", max_price, SearchOperation.LESS_THAN_EQUAL));
		} 
		else if (q.isEmpty() && min_price != null && max_price == null) {
			add(new SearchCriteria("price", min_price, SearchOperation.GREATER_THAN_EQUAL));
		} 
		else if (q.isEmpty() && min_price == null && max_price != null) {
			add(new SearchCriteria("price", max_price, SearchOperation.LESS_THAN_EQUAL));
		} 
		else {
			add(new SearchCriteria("id", "0", SearchOperation.EQUAL));
		}
	}
	

	public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicates = new ArrayList<>();
        
        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                     predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
            } 
            else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                     predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
            } 
            else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                     predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
            } 
            else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                     predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
            } 
            else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                     predicates.add(builder.notEqual( root.get(criteria.getKey()), criteria.getValue()));
            } 
            else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                     predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
            }
            else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                     predicates.add(builder.like(builder.lower(builder.concat(root.get(criteria.getKey()), 
                     root.get(criteria.getK()))),"%" + criteria.getValue().toString().toLowerCase() + "%"));
            } 
            else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                     predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase() + "%"));
            } 
            else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
                     predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase()));
            } 
            else if (criteria.getOperation().equals(SearchOperation.IN)) {
                     predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
            } 
            else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
                     predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}