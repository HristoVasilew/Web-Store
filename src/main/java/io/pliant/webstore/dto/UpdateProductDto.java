package io.pliant.webstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProductDto {

    @NotBlank
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters!")
    private String name;

    @NotNull
    @DecimalMin(value = "0.01", message = "Price should be minimum 0.01!")
    @DecimalMax(value = "10000.0", message = "Price should be maximum 10000.0 !")
    private BigDecimal price;
}
