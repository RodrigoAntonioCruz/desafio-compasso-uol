package com.compasso.uol.services;


import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.compasso.uol.dto.ProductDTO;
import com.compasso.uol.entities.Product;
import com.compasso.uol.repositories.ProductRepository;
import com.compasso.uol.services.exceptions.DataIntegrityException;
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
    public ProductDTO fidById(Long id) {
        Optional<Product> object = productRepository.findById(id);
        Product entity = object.orElseThrow(() -> new ObjectNotFoundException("Objeto solicitado não encontrado!"));
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO insert(ProductDTO objectDTO) {
        Product entity = new Product();
        fromDtoToEntity(objectDTO, entity);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO objectDTO) {
		try {
			Product entity = productRepository.getOne(id);
			fromDtoToEntity(objectDTO, entity);
			entity = productRepository.save(entity);
			return new ProductDTO(entity);
		} catch (ObjectNotFoundException e) {
			throw new ObjectNotFoundException("Objeto solicitado não encontrado!");
		}
    }

    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ObjectNotFoundException("Objeto solicitado não encontrado!");
        }catch (DataIntegrityViolationException e){
        	throw new DataIntegrityException("O produto não pôde ser excluído!");
        }
    }

    private void fromDtoToEntity(ProductDTO objectDTO, Product entity) {
        entity.setName(objectDTO.getName());
        entity.setDescription(objectDTO.getDescription());
        entity.setPrice(objectDTO.getPrice());
    }
    
}
