package io.pliant.webstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Address")
@Table(name = "addresses")
public class AddressEntity extends BaseEntity{

    @Column(nullable = false)
    @Size(min = 1, max = 100)
    private String city;

    @Column(nullable = false)
    @Size(min = 1, max = 100)
    private String street;

    @Column()
    @Range(min = 1, max = 10000)
    private int number;

    @ManyToOne()
    private UserEntity user;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        AddressEntity entity = (AddressEntity) obj;
        if (this.getCity().equals(entity.getCity())) {
            if (this.getStreet().equals(entity.getStreet())) {
                return this.getNumber() == (entity.getNumber());
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("City: %s, Street: %s, Number: %d",
                getCity(),
                getStreet(),
                getNumber());
    }
}
