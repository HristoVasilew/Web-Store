package io.pliant.webstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AddressDto {

    @NotBlank
    @Size(min = 1, max = 100, message = "City name should be between 1 and 100 characters.")
    private String city;

    @NotBlank
    @Size(min = 1, max = 100, message = "Street name should be between 1 and 100 characters.")
    private String street;

    @Range(min = 1, max = 10000, message = "Number should be between 1 and 10000 characters.")
    private int number;
}
