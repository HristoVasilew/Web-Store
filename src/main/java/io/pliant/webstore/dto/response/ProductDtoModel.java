package io.pliant.webstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDtoModel {

    private String barcode;

    private String name;

    private BigDecimal price;

    private LocalDateTime dateUpdated;
}
