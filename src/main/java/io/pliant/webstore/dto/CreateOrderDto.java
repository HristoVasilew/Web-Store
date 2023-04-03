package io.pliant.webstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrderDto {

    @NotBlank
    @Size(min = 2, max = 100, message = "Greeting should be between 2 and 100 characters!")
    private String greeting;

    @NotEmpty
    private List<String> productEntities;

}
