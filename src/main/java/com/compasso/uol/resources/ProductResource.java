package com.compasso.uol.resources;

import com.compasso.uol.dto.ProductDTO;
import com.compasso.uol.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

import javax.validation.Valid;

/**
 *
 * @author Rodrigo da Cruz
 * @version 1.0
 * @since 2021-02-25
 * 
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductResource {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        Page<ProductDTO> list = productService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        ProductDTO objectDTO =  productService.fidById(id);
        return ResponseEntity.ok().body(objectDTO);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO objectDTO){
        objectDTO = productService.insert(objectDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objectDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(objectDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@Valid @RequestBody ProductDTO objectDTO, @PathVariable Long id){
        objectDTO = productService.update(id, objectDTO);
        return ResponseEntity.ok().body(objectDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

}
