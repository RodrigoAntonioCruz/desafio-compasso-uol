package com.compasso.uol.services;


import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.compasso.uol.dto.ProductDTO;
import com.compasso.uol.entities.Product;
import com.compasso.uol.repositories.ProductRepository;
import com.compasso.uol.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Rodrigo da Cruz
 * @version 1.0
 * @since 2021-02-25
 * 
 */

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
       
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
           Page<Product> list =  productRepository.findAll(pageRequest);
        return list.map(ProductDTO::new);
    }
    
    @Transactional(readOnly = true)
    public List<ProductDTO> findSearch(Double min_price, Double max_price, String q){
    	   List<ProductDTO> list =  productRepository.searchIgnoreCase(min_price, max_price, q);
        return list;
    }
    
    @Transactional(readOnly = true)
    public ProductDTO fidById(Long id) {
           Optional<Product> object = productRepository.findById(id);
           Product entity = object.orElseThrow(() -> new ObjectNotFoundException("Objeto solicitado não encontrado!"));
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO insert(ProductDTO object) {
           Product entity = new Product();
           fromDtoToEntity(object, entity);
           entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO object) {
		try {
			Product entity = productRepository.getOne(id);
			fromDtoToEntity(object, entity);
			entity = productRepository.save(entity);
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ObjectNotFoundException("Objeto solicitado não encontrado!");
		}
    }

    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        }catch (RuntimeException e){
            throw new ObjectNotFoundException("Objeto solicitado não encontrado!");
        }
    }

    private void fromDtoToEntity(ProductDTO object, Product entity) {
        entity.setName(object.getName());
        entity.setDescription(object.getDescription());
        entity.setPrice(object.getPrice());
    }  
}
