package com.compasso.uol.dto;


import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import com.compasso.uol.entities.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Rodrigo da Cruz
 * @version 1.0
 * @since 2021-02-25
 * 
 */

@Data
@NoArgsConstructor
public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @ApiModelProperty(position = 0)
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(position = 1)
    @NotEmpty(message = "Preenchimento obrigatório")
    @Valid
    @Length(min = 1, max = 255, message = "O tamanho deve ser entre 1 e 255 caracteres")
    private String name;

    @Column(nullable = false)
    @ApiModelProperty(position = 2)
    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 1, max = 255, message = "O tamanho deve ser entre 1 e 255 caracteres")
    private String description;

    @Column(nullable = false)
    @ApiModelProperty(position = 3)
    @NotNull(message = "Preenchimento obrigatório")
    @PositiveOrZero(message = "O preço deve ser um número positivo")
    private Double price;

    public ProductDTO(Long id, String name, String description, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
    }

}
