package io.pliant.webstore.model;

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

@Entity(name = "Order")
@Table(name = "orders")
public class OrderEntity extends BaseEntity{

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    @DecimalMin("0.01")
    @DecimalMax("10000.00")
    private BigDecimal totalPrice;

    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String greeting;

    @ManyToOne()
    private UserEntity user;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "barcode", referencedColumnName = "barcode"))
    private List<ProductEntity> products;

    @PrePersist
    public void prePersist() {
        this.date = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.date = LocalDateTime.now();
    }
}
