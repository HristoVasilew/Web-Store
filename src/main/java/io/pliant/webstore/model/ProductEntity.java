package io.pliant.webstore.model;

import io.pliant.webstore.convertor.BarcodeJpaConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "Product")
@Table(name = "products")

public class ProductEntity{

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "barcode", nullable = false, unique = true))
    })
    @Convert(converter = BarcodeJpaConverter.class, attributeName = "barcode")
    private Barcode barcode;

    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String name;

    @DecimalMin("0.01")
    @DecimalMax("10000.00")
    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime dateUpdated;

    @ManyToMany(mappedBy = "products")
    private List<OrderEntity> orders;

    @PrePersist
    public void prePersist() {
        this.dateUpdated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }
}
