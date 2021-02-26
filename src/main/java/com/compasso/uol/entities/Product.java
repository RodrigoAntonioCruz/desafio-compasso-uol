package com.compasso.uol.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Rodrigo da Cruz
 * @version 1.0
 * @since 2021-02-25
 * 
 */

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ApiModelProperty(position = 0)
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(position = 1)
    @NotNull(message = "Preenchimento obrigatório")
    @Length(min = 1, max = 255, message = "O tamanho deve ser entre 1 e 255 caracteres")
    private String name;

    @Column(nullable = false)
    @ApiModelProperty(position = 2)
    @NotNull(message = "Preenchimento obrigatório")
    @Length(min = 1, max = 255, message = "O tamanho deve ser entre 1 e 255 caracteres")
    private String description;

    @Column(nullable = false)
    @ApiModelProperty(position = 3)
    @NotNull(message = "Preenchimento obrigatório")
    @Positive(message = "O preço deve ser um número inteiro e positivo")
    private Double price;

}
