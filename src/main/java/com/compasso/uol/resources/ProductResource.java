package com.compasso.uol.resources;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.compasso.uol.dto.ProductDTO;
import com.compasso.uol.entities.Product;
import com.compasso.uol.services.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Rodrigo da Cruz
 * @version 1.0
 * @since 2021-02-25
 * 
 */

@RestController
@RequiredArgsConstructor
@Api(tags = { "Produtos" })
@RequestMapping("/api/products")
public class ProductResource {

	private final ProductService productService;

	@GetMapping
	@ApiOperation("Lista de produtos")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	public ResponseEntity<Page<ProductDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			                                        @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			                                        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
			                                        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<ProductDTO> list = productService.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/search")
	@ApiOperation("Lista de produtos filtrados")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
	public ResponseEntity<?> search(@RequestParam(value = "q", defaultValue = "", required = false) String q,
			                        @RequestParam(value = "min_price", required = false) Double min_price,
			                        @RequestParam(value = "max_price", required = false) Double max_price) {
		List<Product> list = productService.findSearch(q, min_price, max_price);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/{id}")
	@ApiOperation("Busca de um produto por ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
		                	@ApiResponse(code = 404, message = "Não encontrado")})
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		ProductDTO object = productService.fidById(id);
		return ResponseEntity.ok().body(object);
	}

	@PostMapping
	@ApiOperation("Criação de um produto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			                @ApiResponse(code = 201, message = "Criado com successo"),
			                @ApiResponse(code = 400, message = "Requisição Inválida")})
	public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO object) {
		object = productService.insert(object);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(object.getId()).toUri();
		return ResponseEntity.created(uri).body(object);
	}

	@PutMapping("/{id}")
	@ApiOperation("Atualização de um produto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			                @ApiResponse(code = 400, message = "Requisição Inválida"),
			                @ApiResponse(code = 404, message = "Não encontrado")})
	public ResponseEntity<ProductDTO> update(@Valid @RequestBody ProductDTO object, @PathVariable Long id) {
		object = productService.update(id, object);
		return ResponseEntity.ok().body(object);
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Deleção de um produto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			                @ApiResponse(code = 404, message = "Não encontrado") })
	public ResponseEntity<ProductDTO> delete(@Valid @PathVariable Long id) {
		productService.delete(id);
		return ResponseEntity.ok().build();
	}
}
